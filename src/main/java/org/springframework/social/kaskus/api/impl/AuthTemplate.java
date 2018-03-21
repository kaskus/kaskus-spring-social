package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.AuthOperations;
import org.springframework.web.client.RestTemplate;

public class AuthTemplate extends AbstractKaskusOperations implements AuthOperations {

    private final RestTemplate restTemplate;

    public AuthTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp, String apiBaseUrl) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
        this.setAPI_URL_BASE(apiBaseUrl);
    }

    @Override
    public void deleteTokenAndSession() {
        restTemplate.delete(buildUri("/authentication"));
    }
}
