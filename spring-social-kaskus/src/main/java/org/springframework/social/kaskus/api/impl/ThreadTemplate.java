package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.HotThread;
import org.springframework.social.kaskus.api.Thread;
import org.springframework.social.kaskus.api.ThreadOperations;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ThreadTemplate extends AbstractKaskusOperations implements ThreadOperations {

    private final RestTemplate restTemplate;

    public ThreadTemplate(RestTemplate restTemplate, boolean isUserAuthorized, boolean isAppAuthorized, String apiBaseUrl) {
        super(isUserAuthorized, isAppAuthorized);
        this.setAPI_URL_BASE(apiBaseUrl);
        this.restTemplate = restTemplate;
    }

    public List<Thread> getCurrentHotThread() {
        requireUserAuthorization();
        HotThread hotThread = restTemplate.getForObject(buildUri("/v1/hot_threads?output=json"), HotThread.class);
        return hotThread.getData();
    }

}
