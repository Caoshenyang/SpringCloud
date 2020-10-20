package com.yang.springsecurity.provider;

import com.yang.springsecurity.exception.VerificationCodeException;
import com.yang.springsecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationProvider extends DaoAuthenticationProvider {

    //构造方法注入UserDetailsService和PasswordEncoder
    public MyAuthenticationProvider(MyUserDetailsService myUserDetailService, MyPasswordEncoder myPasswordEncoder) {
        this.setUserDetailsService(myUserDetailService);
        this.setPasswordEncoder(myPasswordEncoder);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //实现图形验证码逻辑
        //获取详细信息
        MyWebAuthenticationDetails details = (MyWebAuthenticationDetails) authentication.getDetails();
        //验证码错误，抛出异常
        if (!details.getImageCodeIsRight()){
            throw new VerificationCodeException("验证码错误");
        }
        //调用父类完成密码校验认证
        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
