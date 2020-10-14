package com.yang.springsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@RestController
@SpringBootApplication
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityApplication.class, args);
    }

    @GetMapping("/hi")
    public String hi (){
        return "hi,spring-security";
    }

    @RequestMapping(value = "/test",method = RequestMethod.POST)
    public String login (@RequestParam(value = "username") String username,@RequestParam(value = "password") String password){
        return "登录成功口跳转至此，进行一些逻辑判断。。。";
    }
}
