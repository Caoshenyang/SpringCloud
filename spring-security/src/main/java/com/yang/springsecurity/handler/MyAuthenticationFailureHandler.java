package com.yang.springsecurity.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(401);
        PrintWriter out = httpServletResponse.getWriter();
        out.write("{\n" +
                "    \"error_code\": 401,\n" +
                "    \"error_name\":" + "\"" + e.getClass().getName() + "\",\n" +
                "    \"message\": \"请求失败," + e.getMessage() + "\"\n" +
                "}");
    }
}
