package com.test.springcloud.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import com.test.springcloud.eureka.common.DingtalkNotify;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {

  @Autowired private DingtalkNotify dingtalkNotify;

  @EventListener
  public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
    // 服务断线事件
    String appName = eurekaInstanceCanceledEvent.getAppName();
    String serverId = eurekaInstanceCanceledEvent.getServerId();
    String dingUrl =
        "https://oapi.dingtalk.com/robot/send?access_token=491a980971f73107b286d17957e27f10b19377099296c15479429921def6c41e";
    dingtalkNotify.sendMessage(
        dingUrl, Arrays.asList("18989849801"), String.format("vvp, server %s off line", appName));
  }

  @EventListener
  public void listen(EurekaInstanceRegisteredEvent event) {
    InstanceInfo instanceInfo = event.getInstanceInfo();
    System.out.println("instanceInfo appName is :" + instanceInfo.getAppName());
    System.out.println(instanceInfo);
  }

  @EventListener
  public void listen(EurekaInstanceRenewedEvent event) {
    event.getAppName();
    event.getServerId();
    System.out.println(event.getInstanceInfo());
  }

  @EventListener
  public void listen(EurekaRegistryAvailableEvent event) {
    System.out.println("service is available");
  }

  @EventListener
  public void listen(EurekaServerStartedEvent event) {
    // Server启动
    System.out.println("server start");
  }
}
