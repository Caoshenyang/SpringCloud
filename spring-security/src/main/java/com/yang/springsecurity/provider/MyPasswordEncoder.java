package com.yang.springsecurity.provider;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyPasswordEncoder implements PasswordEncoder {
    private static final PasswordEncoder INSTANCE = new MyPasswordEncoder();

    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return rawPassword.toString().equals(encodedPassword);
    }

    public static PasswordEncoder getInstance() {
        return INSTANCE;
    }

    private MyPasswordEncoder() {
    }
}
