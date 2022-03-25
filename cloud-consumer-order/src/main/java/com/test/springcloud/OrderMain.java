package com.test.springcloud;

import com.test.springcloud.loadbalance.RibbonRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "MCROSERVICE-PAYMENT", configuration = RibbonRule.class)
public class OrderMain {
  public static void main(String[] args) {
    SpringApplication.run(OrderMain.class, args);
  }
}
