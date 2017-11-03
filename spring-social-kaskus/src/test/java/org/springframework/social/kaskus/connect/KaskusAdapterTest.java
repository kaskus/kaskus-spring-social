package org.springframework.social.kaskus.connect;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.kaskus.api.Kaskus;
import org.springframework.social.kaskus.api.KaskusProfile;
import org.springframework.social.kaskus.api.UserOperations;
import org.springframework.social.kaskus.connect.KaskusAdapter;

import static org.junit.Assert.*;

public class KaskusAdapterTest {

    private KaskusAdapter kaskusAdapter = new KaskusAdapter();
    private Kaskus kaskus = Mockito.mock(Kaskus.class);

    @Test
    public void fetchUserProfile() throws Exception {
        UserOperations userOperations = Mockito.mock(UserOperations.class);
        KaskusProfile kaskusProfile = Mockito.mock(KaskusProfile.class);

        Mockito.when(kaskus.userOperations()).thenReturn(userOperations);
        Mockito.when(userOperations.getUserProfile()).thenReturn(kaskusProfile);
        Mockito.when(kaskusProfile.getUserid()).thenReturn(1L);
        Mockito.when(kaskusProfile.getFirstname()).thenReturn("Kas");
        Mockito.when(kaskusProfile.getUsername()).thenReturn("Kus");

        UserProfile userProfile = kaskusAdapter.fetchUserProfile(kaskus);

        assertEquals("1", userProfile.getId());
        assertEquals("Kas", userProfile.getFirstName());
        assertEquals("Kus", userProfile.getUsername());
    }

    @Test
    public void updateStatus() throws Exception {
        assertTrue(true);
    }

}