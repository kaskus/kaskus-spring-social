package org.springframework.social.kaskus.api;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class Thread extends KaskusObject implements Serializable {

    private static final long serialVersionUID = -7741694608579815368L;

    private long forum_id;
    private String title;
    private String thread_id;
    private Optional<List<String>> meta_images = Optional.empty();
    private Optional<Resources> resources = Optional.empty();
    private Optional<Long> item_price = Optional.empty();
    private Optional<Long> discounted_price = Optional.empty();

    public Thread() {}

    public long getForum_id() {
        return forum_id;
    }

    public void setForum_id(long forumId) {
        this.forum_id = forumId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThread_id() {
        return thread_id;
    }

    public void setThread_id(String threadId) {
        this.thread_id = threadId;
    }

    public Optional<List<String>> getMeta_images() {
        return meta_images;
    }

    public void setMeta_images(Optional<List<String>> metaImages) {
        this.meta_images = metaImages;
    }

    public Optional<Resources> getResources() {
        return resources;
    }

    public void setResources(Optional<Resources> resources) {
        this.resources = resources;
    }

    public Optional<Long> getItem_price() {
        return item_price;
    }

    public void setItem_price(Optional<Long> itemPrice) {
        this.item_price = itemPrice;
    }

    public Optional<Long> getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(Optional<Long> discountedPrice) {
        this.discounted_price = discountedPrice;
    }
}
