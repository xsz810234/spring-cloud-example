package com.test.springcloud.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.test.springcloud.pojo.CommonResult;
import com.test.springcloud.service.PaymentService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class OrderFeignController {
  @Autowired private PaymentService paymentService;

  @GetMapping("/consumer/payment/get/{id}")
  public CommonResult queryById(@PathVariable("id") Long id) {
    CommonResult commonResult = paymentService.queryById(id);
    return commonResult;
  }

  @GetMapping("/payment/simulation/timeout")
  public CommonResult testTimeout() {
    CommonResult commonResult = null;
    try {
      commonResult = paymentService.testTimeout();
    } catch (Exception exception) {
      exception.printStackTrace();
      commonResult = new CommonResult();
      commonResult.setCode(408);
      commonResult.setMessage("链接超时，请稍后再试");
    }

    return commonResult;
  }

  @GetMapping("/consumer/hystrix/ok/{id}")
  @HystrixCommand
  CommonResult paymentInfo_OK(@PathVariable("id") Integer id) {
    log.info("i am ok request");
    return paymentService.paymentInfo_OK(id);
  }

  @GetMapping("/consumer/payment/hystrix/timeout/{id}")
  @HystrixCommand
  public CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id) {
    System.out.println("start call api : " + new Date().getTime());
    log.info("order value id is : " + id);
    CommonResult s = paymentService.paymentInfo_TimeOut(id);
    System.out.println("after call api : " + new Date().getTime());
    return s;
  }
}
