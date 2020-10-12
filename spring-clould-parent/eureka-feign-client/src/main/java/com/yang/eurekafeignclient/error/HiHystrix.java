package com.yang.eurekafeignclient.error;

import com.yang.eurekafeignclient.feignclient.EurekaClientFeign;
import org.springframework.stereotype.Component;

@Component
public class HiHystrix implements EurekaClientFeign {
    @Override
    public String sayHiFromClientEureka(String name) {
        return "hi,"+name+",sorry,error";
    }
}
