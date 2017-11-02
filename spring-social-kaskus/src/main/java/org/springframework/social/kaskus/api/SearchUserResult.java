package org.springframework.social.kaskus.api;

import java.io.Serializable;
import java.util.List;

public class SearchUserResult extends KaskusObject implements Serializable {

    private static final long serialVersionUID = 3594472013806226443L;

    private int numFound;
    private int start;
    private double querytime;
    private int current_page;
    private int total_page;
    private int per_page;
    private List<SearchUser> item;

    public int getNumFound() {
        return numFound;
    }

    public int getStart() {
        return start;
    }

    public double getQuerytime() {
        return querytime;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public int getTotal_page() {
        return total_page;
    }

    public int getPer_page() {
        return per_page;
    }

    public List<SearchUser> getItem() {
        return item;
    }
}
