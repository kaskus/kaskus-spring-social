package org.springframework.social.kaskus.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.impl.KaskusTemplate;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;

public class KaskusServiceProvider extends AbstractOAuth1ServiceProvider<Kaskus> {

    private static final Logger logger = LoggerFactory.getLogger(KaskusServiceProvider.class);

    private String apiBaseUrl;

    public KaskusServiceProvider(String consumerKey, String consumerSecret, String apiBaseUrl) {
        super(consumerKey, consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
                apiBaseUrl + "/token",
                apiBaseUrl + "/authorize",
                apiBaseUrl + "/accesstoken"
        ));
        this.apiBaseUrl = apiBaseUrl;
        logger.info("Connected to API URL: " + this.apiBaseUrl);
    }

    public Kaskus getApi(String accessToken, String secret) {
        return new KaskusTemplate(getConsumerKey(), getConsumerSecret(), accessToken, secret, apiBaseUrl);
    }
}
