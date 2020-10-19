package com.yang.springsecurity.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码校验失败异常
 */
public class VerificationCodeException extends AuthenticationException {
    public VerificationCodeException(String msg) {
        super(msg);
    }
}


