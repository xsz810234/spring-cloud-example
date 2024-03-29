package com.test.springcloud.eureka.server;

import com.netflix.appinfo.InstanceInfo;
import com.test.springcloud.eureka.common.DingtalkNotify;
import java.util.Arrays;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateChangeListener {

  @Autowired private DingtalkNotify dingtalkNotify;

  private Long startErrorTimestamp;

  @EventListener
  public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
    // 服务断线事件
    String appName = eurekaInstanceCanceledEvent.getAppName();
    String serverId = eurekaInstanceCanceledEvent.getServerId();
    if (startErrorTimestamp == null) {
      startErrorTimestamp = new Date().getTime();
    }
    Long now = new Date().getTime();
    if ((now - startErrorTimestamp) / (1000 * 60) > 5) {
      String dingUrl =
          "https://oapi.dingtalk.com/robot/send?access_token=491a980971f73107b286d17957e27f10b19377099296c15479429921def6c41e";
      dingtalkNotify.sendMessage(
          dingUrl, Arrays.asList("18989849801"), String.format("vvp, server %s off line", appName));
      startErrorTimestamp = null;
    }
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
    System.out.println("instance renew : " + event.getInstanceInfo());
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
