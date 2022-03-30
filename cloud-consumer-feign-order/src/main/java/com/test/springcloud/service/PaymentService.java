package com.test.springcloud.service;

import com.test.springcloud.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient(value = "MCROSERVICE-PAYMENT")
public interface PaymentService {

    @GetMapping("/payment/get/{id}")
    public CommonResult queryById(@PathVariable("id") Long id);

    @GetMapping("/payment/simulation/timeout")
    public CommonResult testTimeout();
}
