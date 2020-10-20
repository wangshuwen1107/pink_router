import 'package:flutter/material.dart';
import 'pink_router_channel.dart';

class PinkRouter {

  static PinkRouterChannel _channel;
  static Map<String, WidgetBuilder> _routerMap;
  static String _scheme;

  static void init(Map<String, WidgetBuilder> routersMap, {scheme = "pink"}) {
    if (null != routersMap) {
      _routerMap = routersMap;
    }
    if (null == _channel) {
      _channel = PinkRouterChannel();
    }
    if (null == _scheme) {
      _scheme = scheme;
    }
    List<String> routerList = List();
    _routerMap.forEach((key, value) {
      routerList.add(key);
    });
    _channel.registerToNative(routerList);
  }

  static void page(String url, {Map<String, dynamic> params}) {
    Uri realUrl = Uri.parse(url);
    if (null == realUrl) {
      realUrl = Uri.parse("$_scheme");
      print("❌ parse uri $url");
      return;
    }
    print("✌️Route Page url = ${getUrlStrWithoutParams(realUrl)}");
    Map<String, String> urlParams = Uri.splitQueryString(realUrl.query);
    if (null == params) {
      params = {};
    }
    if (null != urlParams && urlParams.isNotEmpty) {
      params.addAll(urlParams);
    }
    print("✌️Route Page params = $params");
    if (_routerMap.containsKey(url)) {
      print("️Route Page params = $params");
    }
    _channel.page(url, params);
  }

  static String getUrlStrWithoutParams(Uri uri) {
    if (null == uri) {
      return "";
    }
    return uri.scheme + "://" + uri.host + "/" + uri.path;
  }
}
