package com.test.springcloud.eureka.common;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class DingtalkNotify {

  public String sendMessage(String dingTalkUrl, List<String> atPersonPhone, String content) {
    String result = null;
    try {
      String reqStr = null;
      // 是否通知所有人
      reqStr = buildReqStr(content, false, atPersonPhone);

      // 组装请求内容

      // 推送消息（http请求）
      result = HttpUtil.post(dingTalkUrl, reqStr);

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  private String buildReqStr(String content, boolean isAtAll, List<String> mobileList) {
    // 消息内容
    Map<String, String> contentMap = new HashMap<>();
    contentMap.put("content", content);

    // 通知人
    Map<String, Object> atMap = new HashMap<>();
    // 1.是否通知所有人
    atMap.put("isAtAll", isAtAll);
    // 2.通知具体人的手机号码列表
    atMap.put("atMobiles", mobileList);

    Map<String, Object> reqMap = new HashMap<>();
    reqMap.put("msgtype", "text");
    reqMap.put("text", contentMap);
    reqMap.put("at", atMap);

    return JSON.toJSONString(reqMap);
  }
}
