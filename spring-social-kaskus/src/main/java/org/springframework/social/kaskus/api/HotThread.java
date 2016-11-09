package org.springframework.social.kaskus.api;

import java.util.List;

public class HotThread extends KaskusObject {

    private List<Thread> data;

    public List<Thread> getData() {
        return data;
    }
}
