import 'package:flutter/services.dart';

const String kRouterChannelName = "pink_router_channel";

const String kRegisterMethodName = "register";
const String kPageMethodName = "push";

class PinkRouterChannel {

  MethodChannel _channel;

  PinkRouterChannel() {
    _channel = MethodChannel(kRouterChannelName);
  }

  void registerToNative(List<String> routerUrlList) {
    _channel.invokeMethod(kRegisterMethodName, routerUrlList);
  }

  Future<dynamic> push(String url, Map<String, dynamic> params) {
    Map args = {
      "url": url,
      "params": params,
    };
    return _channel.invokeMethod(kPageMethodName, args);
  }


}
