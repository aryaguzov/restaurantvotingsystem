package com.restaurant.votingsystem;

import com.restaurant.votingsystem.model.User;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class TestUtil {
    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getName(), user.getPassword());
    }
}
