package com.jacobs.service;

import com.jacobs.module.User;
import org.springframework.stereotype.Component;

/**
 * Created by lichao on 16/7/20.
 */
@Component
public class DemoService {

    public boolean insertUserName(User user) {
        // StringCommand.getJedis(BaseConstant.REDIS_NAME)
        // .setWithExpire("username", 100, user.getFirstName());
        // return true;
        return false;
    }

    public String getUserNameForTest() {
        return "test";
    }

    public String getUserNameFromCache() {
        // try {
        // return StringCommand.getJedis(BaseConstant.REDIS_NAME)
        // .get("username");
        // } catch (Exception ex) {
        // return null;
        // }
        return null;
    }

    // @SuppressWarnings("uncheked")
    // public PageableResponse<String> getPageableResponse() {
    // return PageableResponse
    // .builder()
    // .results(Lists.newArrayList())
    // .totalElements(0)
    // .totalPages(0)
    // .build();
    // }
}
