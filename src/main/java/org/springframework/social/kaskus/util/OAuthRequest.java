package org.springframework.social.kaskus.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class OAuthRequest {

    private static final String CHARSET = "UTF-8";
    private static final String SIGNATURE_METHOD = "HMAC-SHA1";
    private static final String HMAC_SHA1_MAC_NAME = "HmacSHA1";
    private static final String OAUTH_VERSION = "1.0";

    private String targetUrl;
    private String consumerKey, consumerSecret;
    private String oauthTokenKey, oauthTokenSecret;
    private long timestamp, nonce;
    private HttpMethod httpMethod;

    public OAuthRequest(String targetUrl, String consumerKey, String consumerSecret, HttpMethod httpMethod) {
        this.targetUrl = targetUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.httpMethod = httpMethod;
        this.timestamp = timestampGenerator.generateTimestamp();
        this.nonce = timestampGenerator.generateNonce(timestamp);
    }

    public OAuthRequest(String targetUrl, String consumerKey, String consumerSecret, String oauthTokenKey, HttpMethod httpMethod) {
        this(targetUrl, consumerKey, consumerSecret, httpMethod);
        this.oauthTokenKey = oauthTokenKey;
    }

    public OAuthRequest(String targetUrl, String consumerKey, String consumerSecret, String oauthTokenKey, String oauthTokenSecret, HttpMethod httpMethod) {
        this(targetUrl, consumerKey, consumerSecret, oauthTokenKey, httpMethod);
        this.oauthTokenSecret = oauthTokenSecret;
    }

    public HttpHeaders buildAuthorizationHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", getAuthString());

        return headers;
    }

    private String getAuthString() {
        StringBuilder sb = new StringBuilder();

        sb.append("OAuth ")
                .append("oauth_consumer_key=\"" + this.consumerKey + "\",")
                .append("oauth_signature_method=\"" + SIGNATURE_METHOD + "\",")
                .append("oauth_timestamp=\"" + Long.toString(timestamp) + "\",")
                .append("oauth_nonce=\"" + Long.toString(nonce) + "\",")
                .append("oauth_version=\"" + OAUTH_VERSION + "\",")
                .append("oauth_signature=\"" + getSignature() + "\"");

        if (this.oauthTokenKey != null) {
            sb.append(",oauth_token=\"" + this.oauthTokenKey + "\"");
        }

        return sb.toString();
    }

    private String getSignature() {
        String signature = "";
        String baseString = getBaseString();
        String compositeKey = this.consumerSecret + "&" + (this.oauthTokenSecret != null ? this.oauthTokenSecret : "");

        try {
            Mac mac = Mac.getInstance(HMAC_SHA1_MAC_NAME);
            SecretKeySpec secret = new SecretKeySpec(compositeKey.getBytes(), mac.getAlgorithm());
            mac.init(secret);

            signature = java.util.Base64.getEncoder().encodeToString(mac.doFinal(baseString.getBytes()));
            signature = URLEncoder.encode(signature, CHARSET);
        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error getting HMAC-SHA1 algorithm " + e.getMessage());
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            System.out.println("Error invalid key  " + e.getMessage());
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Error encoding signature  " + e.getMessage());
            e.printStackTrace();
        }

        return signature;
    }

    private String getBaseString() {
        String method = urlEncode(httpMethod.name());
        String endpoint = urlEncode(this.targetUrl);
        String queryString = urlEncode(getQueryString());

        return method + "&" + endpoint + "&" + queryString;
    }

    private String getQueryString() {
        List<String> queryString = new ArrayList<>();
        queryString.add("oauth_consumer_key=" + this.consumerKey);
        queryString.add("oauth_nonce=" + this.nonce);
        queryString.add("oauth_signature_method=" + SIGNATURE_METHOD);
        queryString.add("oauth_timestamp=" + String.valueOf(this.timestamp));
        queryString.add("oauth_version=" + OAUTH_VERSION);

        Collections.sort(queryString);
        return implode("&", queryString.toArray(new String[0]));
    }

    private String urlEncode(String text) {
        try {
            return URLEncoder.encode(text, CHARSET)
                    .replace("+", "%20")
                    .replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException e) {
            //should never happen
            return null;
        }
    }

    private String implode(String delimiter, String... stringList) {
        return Arrays.stream(stringList)
                .collect(Collectors.joining(delimiter));
    }

    private TimestampGenerator timestampGenerator = new DefaultTimestampGenerator();

    private static class DefaultTimestampGenerator implements TimestampGenerator {

        public long generateTimestamp() {
            return System.currentTimeMillis() / MODIFIER;
        }

        public long generateNonce(long timestamp) {
            return timestamp + RANDOM.nextInt(BOUND);
        }

        static final int MODIFIER = 1000;

        static final SecureRandom RANDOM = new SecureRandom();

        static final int BOUND = 1000000;

    }

    interface TimestampGenerator {

        long generateTimestamp();

        long generateNonce(long timestamp);

    }

    // For unit test only
    void setTimestamp(TimestampGenerator timestampGenerator) {
        this.timestamp = timestampGenerator.generateTimestamp();
        this.nonce = timestampGenerator.generateNonce(timestamp);
    }
}
