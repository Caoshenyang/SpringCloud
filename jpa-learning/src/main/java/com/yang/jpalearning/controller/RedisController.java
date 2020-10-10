package com.yang.jpalearning.controller;

import com.yang.jpalearning.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/redis")
@RestController
public class RedisController {

    @Autowired
    RedisDao redisDao;

    @GetMapping("/test")
    public String redisTest() {
        redisDao.setKey("name", "张三");
        redisDao.setKey("age", "18");
        return redisDao.getValue("name") + ":" + redisDao.getValue("age");
    }
}
