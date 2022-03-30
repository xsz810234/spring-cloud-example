package com.test.springcloud.controller;


import com.test.springcloud.pojo.CommonResult;
import com.test.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFeignController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult queryById(@PathVariable("id") Long id){
        CommonResult commonResult = paymentService.queryById(id);
        return commonResult;
    }

    @GetMapping("/payment/simulation/timeout")
    public CommonResult testTimeout(){
        CommonResult commonResult = null;
        try {
            commonResult = paymentService.testTimeout();
        }catch (Exception exception){
            exception.printStackTrace();
            commonResult = new CommonResult();
            commonResult.setCode(408);
            commonResult.setMessage("链接超时，请稍后再试");
        }

        return commonResult;
    }
}
