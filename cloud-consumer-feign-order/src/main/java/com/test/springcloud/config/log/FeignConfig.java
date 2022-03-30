package com.test.springcloud.config.log;

import feign.Logger;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    public Logger.Level feignLogLevel(){
        return Logger.Level.BASIC;
    }
}
