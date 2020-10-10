package com.yang.jpalearning.controller;

import com.yang.jpalearning.entity.User;
import com.yang.jpalearning.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "用户信息",notes = "根据名称查询用户信息")
    @GetMapping("/username/{username}")
    public User getUser(@PathVariable("username") String username){
        return userService.findByUsername(username);
    }
}
