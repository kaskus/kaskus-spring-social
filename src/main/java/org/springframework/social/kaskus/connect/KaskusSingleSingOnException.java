package org.springframework.social.kaskus.connect;

public class KaskusSingleSingOnException extends RuntimeException {

    static final long serialVersionUID = -7034897190745766940L;

    public KaskusSingleSingOnException(String message) {
        super(message);
    }

    public KaskusSingleSingOnException(String message, Throwable ex) {
        super(message, ex);
    }
}
