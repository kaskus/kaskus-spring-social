package org.springframework.social.kaskus.connect;

import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.impl.KaskusTemplate;
import org.springframework.social.oauth1.AbstractOAuth1ServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;

public class KaskusServiceProvider extends AbstractOAuth1ServiceProvider<Kaskus> {

    private String apiBaseUrl;

    public KaskusServiceProvider(String consumerKey, String consumerSecret, String apiUrl) {
        super(consumerKey, consumerSecret, new OAuth1Template(consumerKey, consumerSecret,
                apiUrl + "/token",
                apiUrl + "/authorize",
                apiUrl + "/accesstoken"
        ));
        this.apiBaseUrl = apiUrl;
    }

    public Kaskus getApi(String accessToken, String secret) {
        return new KaskusTemplate(getConsumerKey(), getConsumerSecret(), accessToken, secret, apiBaseUrl);
    }
}
