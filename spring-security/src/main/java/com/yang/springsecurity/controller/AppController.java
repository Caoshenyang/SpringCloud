package com.yang.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟对外公开的Api接口
 */
@RequestMapping("/app/api")
@RestController
public class AppController {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "hi,app.";
    }
}
