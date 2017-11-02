package org.springframework.social.kaskus.api;

import java.io.Serializable;

public class Posts extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 404590704855101877L;

    private String text;

    public Posts() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
