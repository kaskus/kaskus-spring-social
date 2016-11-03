package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.UserOperations;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

public class KaskusTemplate extends AbstractOAuth1ApiBinding implements Kaskus {

    private UserOperations userOperations;

    private RestTemplate clientRestTemplate = null;

    private String apiBaseUrl;

    public KaskusTemplate(String consumerKey, String consumerSecret, String accessToken, String accessTokenSecret, String apiBaseUrl) {
        super(consumerKey, consumerSecret, accessToken, accessTokenSecret);
        this.apiBaseUrl = apiBaseUrl;
        initSubApis();
    }

    public UserOperations userOperations() {
        return userOperations;
    }

    private void initSubApis() {
        this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
    }

    private boolean isAuthorizedForApp() {
        return clientRestTemplate != null;
    }
}
