package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.KaskusProfile;
import org.springframework.social.kaskus.api.UserOperations;
import org.springframework.web.client.RestTemplate;

public class UserTemplate extends AbstractKaskusOperations implements UserOperations {

    private final RestTemplate restTemplate;

    public UserTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp, String apiBaseUrl) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
        this.setAPI_URL_BASE(apiBaseUrl);
    }

    public long getUserId() {
        requireUserAuthorization();
        return getUserProfile().getUserid();
    }

    public String getUsername() {
        requireUserAuthorization();
        return getUserProfile().getUsername();
    }

    public KaskusProfile getUserProfile() {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri("/user?output=json"), KaskusProfile.class);
    }
}
