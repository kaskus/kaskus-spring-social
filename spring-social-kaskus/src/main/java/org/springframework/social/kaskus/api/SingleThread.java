package org.springframework.social.kaskus.api;

import java.io.Serializable;
import java.util.List;

public class SingleThread extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 9038985976556394244L;

    private Thread thread;
    private List<Posts> posts;

    public SingleThread() {}

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }
}
