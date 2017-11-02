package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.SearchOperations;
import org.springframework.social.kaskus.api.ThreadOperations;
import org.springframework.social.kaskus.api.UserOperations;
import org.springframework.social.oauth1.AbstractOAuth1ApiBinding;
import org.springframework.web.client.RestTemplate;

public class KaskusTemplate extends AbstractOAuth1ApiBinding implements Kaskus {

    private UserOperations userOperations;
    private ThreadOperations threadOperations;
    private SearchOperations searchOperations;

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

    private void initSubApis() {
        this.userOperations = new UserTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
        this.threadOperations = new ThreadTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
        this.searchOperations = new SearchTemplate(getRestTemplate(), isAuthorized(), isAuthorizedForApp(), apiBaseUrl);
    }

    private boolean isAuthorizedForApp() {
        return clientRestTemplate != null;
    }
}
