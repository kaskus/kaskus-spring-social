package org.springframework.social.kaskus.api;

import java.util.HashMap;
import java.util.Map;

public class KaskusObject {

    private Map<String, Object> extraData;

    public KaskusObject() {
        this.extraData = new HashMap<>();
    }

    public Map<String, Object> getExtraData() {
        return extraData;
    }

    protected void add(String key, Object value) {
        extraData.put(key, value);
    }
}
