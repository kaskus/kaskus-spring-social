package org.springframework.social.kaskus.api;

import org.springframework.social.ApiBinding;

public interface Kaskus extends ApiBinding {

    UserOperations userOperations();

    ThreadOperations threadOperations();
}
