import 'package:flutter/services.dart';

const String kRouterChannelName = "pink_router_channel";

const String kRegisterMethodName = "register";
const String kPageMethodName = "page";

class PinkRouterChannel {

  MethodChannel _channel;

  PinkRouterChannel() {
    _channel = MethodChannel(kRouterChannelName);
  }

  void registerToNative(List<String> routerUrlList) {
    _channel.invokeMethod(kRegisterMethodName, routerUrlList);
  }


  void page(String url, Map<String, dynamic> params) {
    Map args = {
      "url": url,
      "params": params
    };
    _channel.invokeMethod(kPageMethodName,args);
  }


}