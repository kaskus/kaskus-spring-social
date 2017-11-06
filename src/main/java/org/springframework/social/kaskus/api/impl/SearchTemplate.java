package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.SearchOperations;
import org.springframework.social.kaskus.api.SearchUser;
import org.springframework.social.kaskus.api.SearchUserResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class SearchTemplate extends AbstractKaskusOperations implements SearchOperations {

    private final RestTemplate restTemplate;

    public SearchTemplate(RestTemplate restTemplate, boolean isUserAuthorized, boolean isAppAuthorized, String apiBaseUrl) {
        super(isUserAuthorized, isAppAuthorized);
        this.restTemplate = restTemplate;
        this.setAPI_URL_BASE(apiBaseUrl);
    }

    public List<SearchUser> searchUser(String username) {
        return getSearchUserResult(username).getItem();
    }

    public SearchUserResult getSearchUserResult(String username) {
        requireUserAuthorization();
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.set("q", username);
        parameters.set("output", "json");
        return restTemplate.getForObject(buildUri("/search/user", parameters), SearchUserResult.class);
    }
}
