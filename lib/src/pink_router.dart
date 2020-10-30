import 'package:flutter/material.dart';
import 'package:pink_router/src/pink_intent.dart';
import 'pink_router_channel.dart';
import 'package:flutter/widgets.dart';
import 'pink_util.dart';

class PinkRouter {
  static Map<String, PinkIntent> _routerMap = Map();

  static String get scheme => _scheme;

  static PinkRouterChannel _channel;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
    _channel = PinkRouterChannel();
  }

  static void register(
      {Map<String, WidgetBuilder> pages, Map<String, MethodBlock> methods}) {
    _routerMap.clear();
    List<String> routerList = List();
    //page
    if (null != pages) {
      pages.forEach((key, value) {
        String urlStr = PinkUtil.getUrlKey(key);
        Uri uri = Uri.parse(urlStr);
        assert(
            PinkUtil.checkUri(uri), "Route Register Error scheme://host/path");
        _routerMap[urlStr] = PinkIntent.uri(urlStr, builder: value);
        routerList.add(urlStr);
      });
    }
    //method
    if (null != methods) {
      methods.forEach((key, value) {
        String urlStr = PinkUtil.autoCompleteUrl(key);
        Uri uri = Uri.parse(urlStr);
        assert(
            PinkUtil.checkUri(uri), "Route Register Error scheme://host/path");
        _routerMap[urlStr] = PinkIntent.uri(urlStr, block: value);
        routerList.add(urlStr);
      });
    }
    _channel.registerToNative(routerList);
  }

  static Future<dynamic> push(String url, {Map<String, dynamic> params}) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query, extraParams: params);
    PinkIntent intent = _routerMap[urlStr];
    if (null != intent) {
      print("🐳 Flutter-> $urlStr   params = $allParams");
      return intent.start(allParams);
    }
    print("🍠 Native-> $urlStr  params = $allParams");
    return _channel.push(urlStr, allParams);
  }



}
