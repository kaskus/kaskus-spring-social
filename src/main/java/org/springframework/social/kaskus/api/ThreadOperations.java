package org.springframework.social.kaskus.api;

public interface ThreadOperations {

    MultipleThreads getMyForumThread(long userId, int page);
    MultipleThreads getMyActiveWts(long userId, int page);
    SingleThread getForumThread(String threadId);
    SingleThread getLapak(String threadId);
    SingleThread getOldLapak(String threadId);
    SingleThread getPostLapak(String threadId);
}
