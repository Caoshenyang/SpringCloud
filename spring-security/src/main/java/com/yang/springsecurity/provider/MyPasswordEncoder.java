package com.yang.springsecurity.provider;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class MyPasswordEncoder extends BCryptPasswordEncoder {
    private Pattern BCRYPT_PATTERN = Pattern
            .compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()){
            return rawPassword.toString().matches(encodedPassword);
        }
        return super.matches(rawPassword, encodedPassword);
    }
}
