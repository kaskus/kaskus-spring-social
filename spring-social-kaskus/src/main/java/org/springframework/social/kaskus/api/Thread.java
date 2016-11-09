package org.springframework.social.kaskus.api;

public class Thread extends KaskusObject {

    private String threadId;
    private String title;
    private String forumName;
    private long forumId;
    private String username;
    private long threadType;
    private long hotThreadStartTimestamp;
    private long hotThreadStartDate;
    private long hotThreadEndDate;
    private int hotThreadFlag;
    private int voteNum;
    private int voteTotal;
    private double rating;
    private String image;

    public String getThreadId() {
        return threadId;
    }

    public String getTitle() {
        return title;
    }

    public String getForumName() {
        return forumName;
    }

    public long getForumId() {
        return forumId;
    }

    public String getUsername() {
        return username;
    }

    public long getThreadType() {
        return threadType;
    }

    public long getHotThreadStartTimestamp() {
        return hotThreadStartTimestamp;
    }

    public long getHotThreadStartDate() {
        return hotThreadStartDate;
    }

    public long getHotThreadEndDate() {
        return hotThreadEndDate;
    }

    public int getHotThreadFlag() {
        return hotThreadFlag;
    }

    public int getVoteNum() {
        return voteNum;
    }

    public int getVoteTotal() {
        return voteTotal;
    }

    public double getRating() {
        return rating;
    }

    public String getImage() {
        return image;
    }
}
