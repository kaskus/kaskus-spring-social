package org.springframework.social.kaskus.api;

import java.io.Serializable;
import java.util.List;

public class MultipleThreads extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 8748818038463754276L;

    private List<Thread> thread;
    private long total_page;

    public MultipleThreads() {}

    public List<Thread> getThread() {
        return thread;
    }

    public void setThread(List<Thread> thread) {
        this.thread = thread;
    }

    public long getTotal_page() {
        return total_page;
    }

    public void setTotal_page(long totalPage) {
        this.total_page = totalPage;
    }
}
