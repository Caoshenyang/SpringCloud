package com.yang.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟用户相关Api接口
 */
@RequestMapping("/user/api")
@RestController
public class UserController {

    @RequestMapping(value = "hi", method = RequestMethod.GET)
    public String hi() {
        return "hi,user.";
    }
}
