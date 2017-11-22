package org.springframework.social.kaskus.api.impl;

import org.springframework.social.kaskus.api.MultipleThreads;
import org.springframework.social.kaskus.api.SingleThread;
import org.springframework.social.kaskus.api.ThreadOperations;
import org.springframework.web.client.RestTemplate;

public class ThreadTemplate extends AbstractKaskusOperations implements ThreadOperations {

    private static final String MY_THREAD_ENDPOINT = "/mythread/";
    private static final String MY_ACTIVE_WTS_ENDPOINT = "/mywts/active/";
    private static final String FORUM_THREAD_ENDPOINT = "/v1/forum_thread/";
    private static final String LAPAK_ENDPOINT = "/v1/lapak/";
    private static final String OLD_LAPAK_ENDPOINT = "/v1/fjb_thread/";
    private static final String POST_LAPAK_ENDPOINT = "/v1/fjb_post/";

    private static final String OUTPUT_JSON = "output=json";
    private static final String FILTER_FORUM = "filter=forum";
    private static final String FIELD_TEXT = "field=text";

    private final RestTemplate restTemplate;

    public ThreadTemplate(RestTemplate restTemplate, boolean isAuthorizedForUser, boolean isAuthorizedForApp, String apiBaseUrl) {
        super(isAuthorizedForUser, isAuthorizedForApp);
        this.restTemplate = restTemplate;
        this.setAPI_URL_BASE(apiBaseUrl);
    }

    public MultipleThreads getMyForumThread(long userId, int page) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(MY_THREAD_ENDPOINT + userId + "?" + OUTPUT_JSON + "&" + FILTER_FORUM + "&page=" + page), MultipleThreads.class);
    }

    public MultipleThreads getMyActiveWts(long userId, int page) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(MY_ACTIVE_WTS_ENDPOINT + userId + "?" + OUTPUT_JSON +"&page=" + page), MultipleThreads.class);
    }

    public SingleThread getForumThread(String threadId) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(FORUM_THREAD_ENDPOINT + threadId + "?" + OUTPUT_JSON + "&" + FIELD_TEXT), SingleThread.class);
    }

    public SingleThread getLapak(String threadId) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(LAPAK_ENDPOINT + threadId + "?" + OUTPUT_JSON + "&" + FIELD_TEXT), SingleThread.class);
    }

    public SingleThread getOldLapak(String threadId) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(OLD_LAPAK_ENDPOINT + threadId + "?" + OUTPUT_JSON + "&" + FIELD_TEXT), SingleThread.class);
    }

    public SingleThread getPostLapak(String threadId) {
        requireUserAuthorization();
        return restTemplate.getForObject(buildUri(POST_LAPAK_ENDPOINT + threadId + "?" + OUTPUT_JSON + "&" + FIELD_TEXT), SingleThread.class);
    }
}
