package org.springframework.social.kaskus.api;

import java.io.Serializable;
import java.util.List;

public class Resources extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 1998420537414920752L;

    private List<String> images;
    private String thumbnail;

    public Resources() {}

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
