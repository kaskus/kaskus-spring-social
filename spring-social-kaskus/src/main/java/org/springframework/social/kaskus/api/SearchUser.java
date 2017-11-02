package org.springframework.social.kaskus.api;

import java.io.Serializable;

public class SearchUser extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 8733619605843050165L;

    private String userid;
    private String username;

    public String getUserid() {
        return userid;
    }

    public String getUsername() {
        return username;
    }
}
