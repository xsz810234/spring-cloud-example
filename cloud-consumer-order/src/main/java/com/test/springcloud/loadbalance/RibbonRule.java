package com.test.springcloud.loadbalance;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonRule {
  @Bean
  public IRule specificRibbonRule() {
    // 定义为随机
    return new RoundRobinRule();
  }
}
