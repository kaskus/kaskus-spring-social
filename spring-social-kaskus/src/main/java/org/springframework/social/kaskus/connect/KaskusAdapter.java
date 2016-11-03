package org.springframework.social.kaskus.connect;

import org.springframework.social.ApiException;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.KaskusProfile;

public class KaskusAdapter implements ApiAdapter<Kaskus> {

    public boolean test(Kaskus kaskus) {
        try {
            kaskus.userOperations().getUserProfile();
            return true;
        } catch (ApiException e) {
            return false;
        }
    }

    public void setConnectionValues(Kaskus kaskus, ConnectionValues values) {
        KaskusProfile kaskusProfile = kaskus.userOperations().getUserProfile();
        values.setProviderUserId(Long.toString(kaskusProfile.getUserid()));
        values.setDisplayName(kaskusProfile.getUsername());
        values.setImageUrl(kaskusProfile.getProfilepicture());
    }

    public UserProfile fetchUserProfile(Kaskus kaskus) {
        KaskusProfile kaskusProfile = kaskus.userOperations().getUserProfile();
        return new UserProfileBuilder()
                .setId(String.valueOf(kaskusProfile.getUserid()))
                .setName(kaskusProfile.getFirstname())
                .setUsername(kaskusProfile.getUsername())
                .build();
    }

    public KaskusProfile fetchKaskusUserProfile(Kaskus kaskus) {
        KaskusProfile kaskusProfile = kaskus.userOperations().getUserProfile();
        return kaskusProfile;
    }

    public void updateStatus(Kaskus api, String message) {
    }
}
