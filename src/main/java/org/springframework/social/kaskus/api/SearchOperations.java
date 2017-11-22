package org.springframework.social.kaskus.api;

import java.util.List;

public interface SearchOperations {

    List<SearchUser> searchUser(String username);

    SearchUserResult getSearchUserResult(String username);
}
