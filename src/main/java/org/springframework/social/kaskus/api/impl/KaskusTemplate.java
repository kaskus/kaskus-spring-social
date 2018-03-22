package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.*;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

public class KaskusTemplate extends AbstractOAuth1ApiBinding implements Kaskus {

    private UserOperations userOperations;
    private ThreadOperations threadOperations;
    private SearchOperations searchOperations;
    private AuthOperations authOperations;

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

    public ThreadOperations threadOperations() {
        return threadOperations;
    }

    public SearchOperations searchOperations() {
        return searchOperations;
    }

    public AuthOperations authOperations() {
        return authOperations;
    }

    private void initSubApis() {
        this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
        this.threadOperations = new ThreadTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
        this.searchOperations = new SearchTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
        this.authOperations = new AuthTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
    }

    private boolean isAuthorizedForApp() {
        return clientRestTemplate != null;
    }
}
