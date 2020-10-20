package com.yang.springsecurity.provider;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new MyWebAuthenticationDetails(request);
    }
}
