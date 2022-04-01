package com.test.springcloud.service;

import com.test.springcloud.pojo.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ComponentScan
@FeignClient(value = "MCROSERVICE-PAYMENT", fallback = PaymentServiceFallback.class)
public interface PaymentService {

  @GetMapping("/payment/get/{id}")
  public CommonResult queryById(@PathVariable("id") Long id);

  @GetMapping("/payment/simulation/timeout")
  public CommonResult testTimeout();

  /**
   * 正常访问
   *
   * @param id
   * @return
   */
  @GetMapping("/payment/hystrix/ok/{id}")
  CommonResult paymentInfo_OK(@PathVariable("id") Integer id);

  /**
   * 超时访问
   *
   * @param id
   * @return
   */
  @GetMapping("/payment/hystrix/timeout/{id}")
  CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id);
}
