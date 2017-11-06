package org.springframework.social.kaskus.api;

public interface UserOperations {

    long getUserId();

    String getUsername();

    KaskusProfile getUserProfile();

    KaskusProfile getUserProfile(String userid);
}
