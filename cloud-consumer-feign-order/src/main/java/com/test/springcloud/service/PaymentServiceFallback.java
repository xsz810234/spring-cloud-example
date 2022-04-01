package com.test.springcloud.service;

import com.test.springcloud.pojo.CommonResult;
import org.springframework.stereotype.Component;

@Component
public class PaymentServiceFallback implements PaymentService {
  @Override
  public CommonResult queryById(Long id) {
    return new CommonResult(408, String.format("get result by id %s failed", id));
  }

  @Override
  public CommonResult testTimeout() {
    return new CommonResult(408, "timeout test success, service downgrade success");
  }

  @Override
  public CommonResult paymentInfo_OK(Integer id) {
    return new CommonResult(408, "PaymentServiceFallback fall  paymentInfo_OK 服务器出现故障,o(╥﹏╥)o");
  }

  @Override
  public CommonResult paymentInfo_TimeOut(Integer id) {
    return new CommonResult(
        408, "PaymentServiceFallback fall  paymentInfo_TimeOut 服务器出现故障,o(╥﹏╥)o");
  }
}
