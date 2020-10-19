package com.yang.springsecurity.filter;

import com.yang.springsecurity.exception.VerificationCodeException;
import com.yang.springsecurity.handler.MyAuthenticationFailureHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class VerificationCodeFilter extends OncePerRequestFilter {

    private final AuthenticationFailureHandler authenticationFailureHandler = new MyAuthenticationFailureHandler();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //非登录请求不校验验证码
        String requestURI = httpServletRequest.getRequestURI();
        if (!"/myLogin".equalsIgnoreCase(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            try {
                verificationCode(httpServletRequest);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } catch (VerificationCodeException e) {
                System.out.println(e);
                authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    private void verificationCode(HttpServletRequest httpServletRequest) throws VerificationCodeException {
        String captcha = httpServletRequest.getParameter("captcha");
        HttpSession session = httpServletRequest.getSession();
        String captchaCode = (String) session.getAttribute("captcha");
        if (StringUtils.isNotEmpty(captchaCode)) {
            // 校验过一次后清除验证码，不管成功或失败
            session.removeAttribute("captcha");
        }
        //校验不通过抛出异常
        if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(captchaCode) || !captcha.equals(captchaCode))
            throw new VerificationCodeException("图形验证码校验异常");
    }

}
