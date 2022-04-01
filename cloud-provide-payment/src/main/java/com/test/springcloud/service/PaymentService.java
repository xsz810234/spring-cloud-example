package com.test.springcloud.service;

import com.test.springcloud.pojo.Payment;
import org.apache.ibatis.annotations.Param;

public interface PaymentService {

  int create(Payment payment);

  Payment queryById(@Param("id") long id);

  public String paymentInfo_OK(Integer id);

  public String paymentInfo_TimeOut(Integer id);
}
