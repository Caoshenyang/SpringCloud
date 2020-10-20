package com.yang.springsecurity.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private boolean imageCodeIsRight;

    public boolean getImageCodeIsRight(){
        return this.imageCodeIsRight;
    }

    //补充用户提交的验证码和session保存的验证码
    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        String captcha = request.getParameter("captcha");
        HttpSession session = request.getSession();
        String saveCaptcha = (String) session.getAttribute("captcha");
        if (StringUtils.isNotEmpty(saveCaptcha)){
            session.removeAttribute("captcha");
        }
        if (StringUtils.isNotEmpty(captcha) && captcha.equals(saveCaptcha)){
            this.imageCodeIsRight = true;
        }
    }
}
