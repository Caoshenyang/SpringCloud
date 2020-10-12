package com.yang.eurekafeignclient.feignclient;

import com.yang.eurekafeignclient.config.FeignConfig;
import com.yang.eurekafeignclient.error.HiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "eureka-client", configuration = FeignConfig.class,fallback = HiHystrix.class)
public interface EurekaClientFeign {
    @GetMapping("/hi")
    String sayHiFromClientEureka(@RequestParam(value = "name") String name);
}
