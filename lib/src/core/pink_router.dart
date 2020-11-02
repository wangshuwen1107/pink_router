import 'package:flutter/material.dart';
import '../util/pink_util.dart';
import '../channel/channel_send.dart';
import '../channel/channel_receive.dart';
import '../channel/channel_proxy.dart';
import '../module/module_manager.dart';
import '../module/pink_module.dart';
import '../core/router_intent.dart';

class PinkRouter {
  static String get scheme => _scheme;

  static ChannelProxy _channelProxy = ChannelProxy("main");

  static SendChannel sendChannel;

  static ReceiveChannel receiveChannel;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
    sendChannel = SendChannel(_channelProxy);
    receiveChannel = ReceiveChannel(_channelProxy);
  }

  static void register(List<PinkModule> modules) {
    modules.forEach((element) {
      ModuleManager.getInstance().register(element);
    });
  }

  static Future<dynamic> push(String url, {Map<String, dynamic> params}) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);
    RouterIntent intent = ModuleManager.getInstance().getIntent(urlStr);
    if (null != intent) {
      print("🐳 Flutter-> $urlStr   params = $allParams");
      return intent.start(allParams);
    }
    print("🍠 Native-> $urlStr  params = $allParams");
    return sendChannel.navigation(urlStr, allParams);
  }
}
