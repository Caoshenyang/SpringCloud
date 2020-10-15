package com.yang.springsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟后台相关Api接口
 */
@RequestMapping("/admin/api")
@RestController
public class AdminController {

    @RequestMapping(value = "/hi", method = RequestMethod.GET)
    public String hi() {
        return "hi,admin.";
    }
}
