package com.test.springcloud.controller;

import com.test.springcloud.pojo.CommonResult;
import com.test.springcloud.pojo.Payment;
import com.test.springcloud.service.PaymentService;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PaymentController {
  @Autowired private PaymentService paymentService;

  @Value("${server.port}")
  private String servicePort;

  @PostMapping("/payment/create")
  public CommonResult create(@RequestBody Payment dept) {
    int i = paymentService.create(dept);
    log.info("***************插入成功*******" + i);
    if (i > 0) {
      return new CommonResult(200, "插入数据库成功 : ", i);
    } else {
      return new CommonResult(444, "插入数据库失败", null);
    }
  }

  @GetMapping("/payment/get/{id}")
  public CommonResult queryById(@PathVariable("id") Long id) {
    Payment payment = paymentService.queryById(id);
    log.info("***************查询成功*********" + payment);
    if (payment != null) {
      return new CommonResult(200, "查询成功", payment);
    } else {
      return new CommonResult(444, "查询失败", null);
    }
  }

  @GetMapping("/payment/simulation/timeout")
  public CommonResult testTimeout() throws InterruptedException {
    TimeUnit.SECONDS.sleep(3);
    return queryById(1l);
  }

  /**
   * 正常访问
   *
   * @param id
   * @return
   */
  @GetMapping("/payment/hystrix/ok/{id}")
  public CommonResult paymentInfo_OK(@PathVariable("id") Integer id) {
    String result = paymentService.paymentInfo_OK(id);
    log.info("*******************result:" + result);
    return new CommonResult(200, result);
  }

  /**
   * 超时访问
   *
   * @param id
   * @return
   */
  @GetMapping("/payment/hystrix/timeout/{id}")
  public CommonResult paymentInfo_TimeOut(@PathVariable("id") Integer id) {
    String result = paymentService.paymentInfo_TimeOut(id);
    log.info("*********************result:" + result);
    return new CommonResult(200, result);
  }
}
