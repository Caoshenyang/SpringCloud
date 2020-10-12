package com.yang.eurekaribbonclient.controller;

import com.yang.eurekaribbonclient.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RibbonController {

    @Autowired
    RibbonService ribbonService;

    @GetMapping("/hi")
    public String hi(@RequestParam(required = false,defaultValue = "张三")String name){
        return ribbonService.hi(name);
    }
}
