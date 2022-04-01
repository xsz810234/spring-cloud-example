package com.test.springcloud.service.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.test.springcloud.dao.PaymentDao;
import com.test.springcloud.pojo.Payment;
import com.test.springcloud.service.PaymentService;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  @Autowired PaymentDao paymentDao;

  @Override
  public int create(Payment payment) {
    return paymentDao.create(payment);
  }

  @Override
  public Payment queryById(long id) {
    return paymentDao.queryById(id);
  }

  /**
   * 正常访问
   *
   * @param id
   * @return
   */
  @Override
  public String paymentInfo_OK(Integer id) {
    return "线程池:"
        + Thread.currentThread().getName()
        + " paymentInfo_OK,id:"
        + id
        + "\t"
        + "O(∩_∩)O哈哈~";
  }

  /**
   * 超时访问
   *
   * @param id
   * @return
   */
  @HystrixCommand(
      fallbackMethod = "paymentInfo_TimeOutHabdler",
      commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
      })
  public String paymentInfo_TimeOut(Integer id) {
    log.info("id value is " + id);
    int timeNumber = id;
    try {
      // 暂停3秒钟
      System.out.println(new Date().getTime());
      TimeUnit.SECONDS.sleep(id);
      log.info("after sleep");
      System.out.println(new Date().getTime());
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "线程池:"
        + Thread.currentThread().getName()
        + " paymentInfo_TimeOut,id:"
        + id
        + "\t"
        + "O(∩_∩)O哈哈~  耗时(秒)"
        + timeNumber;
  }

  /**
   * 超时访问到这里兜底
   *
   * @param id
   * @return
   */
  public String paymentInfo_TimeOutHabdler(Integer id) {
    return "线程池:"
        + Thread.currentThread().getName()
        + " paymentInfo_TimeOutHabdler,id:"
        + id
        + "\t"
        + "系统繁忙，请稍后再试****o(╥﹏╥)o";
  }
}
