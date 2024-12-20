package com.ajou.op.utils;


import com.ajou.op.domain.user.User;
import com.ajou.op.dto.security.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public class ClassUtils {

    public static User getSafeUserBySafeCast(Authentication authentication) {
        UserDetailsImpl userDetails = getSafeCastInstance(authentication.getPrincipal(), UserDetailsImpl.class);

        return userDetails == null ? null : userDetails.getUser();
    }

    public static <T> T getSafeCastInstance(Object o, Class<T> clazz) {
        return clazz != null && clazz.isInstance(o) ? clazz.cast(o) : null;
    }

}
