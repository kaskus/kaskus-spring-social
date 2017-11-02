package org.springframework.social.kaskus.connect;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.social.connect.support.OAuth1ConnectionFactory;
import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.util.OAuthRequest;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class KaskusConnectionFactory extends OAuth1ConnectionFactory<Kaskus> {

    private static final String ssoEndpoint = "/v1/sso/authentication";

    private final transient String consumerKey;

    private final transient String consumerSecret;

    private final String apiBaseUrl;

    private final RestTemplate restTemplate;

    public KaskusConnectionFactory(String consumerKey, String consumerSecret, String apiBaseUrl, RestTemplate restTemplate) {
        super("kaskus", new KaskusServiceProvider(consumerKey, consumerSecret, apiBaseUrl), new KaskusAdapter());
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.apiBaseUrl = apiBaseUrl;
        this.restTemplate = restTemplate;
    }

    public OAuthToken exchangeForToken(String text) {
        String decrypted = attemptDecryption(text);

        OAuthRequest oAuthRequest = new OAuthRequest(apiBaseUrl + ssoEndpoint, consumerKey, consumerSecret, HttpMethod.POST);
        HttpHeaders headers = oAuthRequest.buildAuthorizationHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, String> additionalParameters = new LinkedMultiValueMap<>();
        additionalParameters.add("key", decrypted.split("-")[0]);
        additionalParameters.add("ip_address", decrypted.split("-")[1]);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(additionalParameters, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(apiBaseUrl + ssoEndpoint, HttpMethod.POST, httpEntity, String.class);
            return createOAuthToken(response.getBody());
        } catch (HttpClientErrorException e) {
            throw new KaskusSingleSingOnException("Error while requesting oauth token: " + e.getResponseBodyAsString());
        }

    }

    private String attemptDecryption(String text) {
        try {
            return AES256.decrypt(text, consumerKey, consumerSecret);
        } catch (IllegalBlockSizeException e) {
            throw new KaskusSingleSingOnException("Error illegal block size: " + e.getMessage());
        } catch (BadPaddingException e) {
            throw new KaskusSingleSingOnException("Error bad padding: " + e.getMessage());
        } catch (Exception e) {
            throw new KaskusSingleSingOnException("Error while decrypting key: " + text + ", message: " + e.getMessage(), e);
        }
    }

    private OAuthToken createOAuthToken(String xmlString) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(SsoResponse.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            StringReader reader = new StringReader(xmlString);
            SsoResponse response = (SsoResponse) jaxbUnmarshaller.unmarshal(reader);
            return new OAuthToken(response.getOauthToken(), response.getOauthTokenSecret());
        } catch (JAXBException e) {
            throw new KaskusSingleSingOnException("Error while parsing xml response: " + e.getMessage());
        } catch (Exception e) {
            throw new KaskusSingleSingOnException("Error while trying to parse xml response: " + e.getMessage(), e);
        }
    }

    private static final class AES256 {

        private static final Logger logger = LoggerFactory.getLogger(AES256.class);

        private static final int INIT_VECTOR_SIZE = 16;

        private static final int KEY_SIZE = 32;

        private static final String UTF8_CHARSET_NAME = "UTF-8";

        private static final String AES_256_CBC_CIPHER_NAME = "AES/CBC/PKCS5Padding";

        private static final String AES_ALGORITHM_NAME = "AES";

        private AES256() {}

        private static String decrypt(String encrypted, String initVector, String key)
                throws BadPaddingException, IllegalBlockSizeException {

            byte[] passwordInBytes;
            byte[] paddedPassword;
            try {
                String trimmedInitVector = initVector.substring(0, INIT_VECTOR_SIZE);

                byte[] initVectorInBytes = trimmedInitVector.getBytes(UTF8_CHARSET_NAME);
                passwordInBytes = key.getBytes(UTF8_CHARSET_NAME);
                paddedPassword = Arrays.copyOf(passwordInBytes, KEY_SIZE);

                Cipher cipher = Cipher.getInstance(AES_256_CBC_CIPHER_NAME);
                SecretKeySpec secretKeySpec = new SecretKeySpec(paddedPassword, AES_ALGORITHM_NAME);
                IvParameterSpec iv = new IvParameterSpec(initVectorInBytes);

                cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);

                byte[] decodedInput = Base64.decodeBase64(encrypted);

                byte[] decryptedBytes = cipher.doFinal(decodedInput);

                return new String(decryptedBytes);
            } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | InvalidKeyException |
                    UnsupportedEncodingException | NoSuchPaddingException e) {
                logger.error("This should never happen: " + e.getMessage());
                return null;
            } finally {
                // zeroize memory locations to prevent Heap Inspection
                passwordInBytes = null;
                paddedPassword = null;
            }
        }
    }

}
