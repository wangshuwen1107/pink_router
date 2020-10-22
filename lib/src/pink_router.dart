import 'package:flutter/material.dart';
import 'pink_router_channel.dart';
import 'pink_navigator_wrapper.dart';
import 'package:flutter/widgets.dart';

typedef MethodBlock<R> = R Function(Map<String, dynamic> params);

class PinkRouter {
  static PinkRouterChannel _channel;
  static Map<String, WidgetBuilder> _routerMap = Map();
  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
    _channel = PinkRouterChannel();
  }

  static void register(
      {Map<String, WidgetBuilder> pageMap,
      Map<String, MethodBlock> methodMap}) {
    _routerMap.clear();
    List<String> routerList = List();
    //page
    if (null != pageMap) {
      pageMap.forEach((key, value) {
        String completeUrlStr = _autoCompleteUrl(key);
        Uri uri = Uri.parse(_autoCompleteUrl(completeUrlStr));
        assert(
            null != uri &&
                uri.scheme.isNotEmpty &&
                uri.host.isNotEmpty &&
                uri.path.isNotEmpty,
            "Route Register Error scheme://host/path");
        _routerMap[completeUrlStr] = value;
        routerList.add(completeUrlStr);
      });
    }
    //method
    _channel.registerToNative(routerList);
  }

  static Future<dynamic> open(String url, {Map<String, dynamic> params}) {
    String completeUrlStr = _autoCompleteUrl(url);
    Uri realUrl = Uri.parse(completeUrlStr);
    if (null == realUrl) {
      print("❌ parse uri $url");
      return Future.value("ERROR");
    }
    String urlWithoutParams = _getUrlStrWithoutParams(realUrl);

    Map<String, String> urlParams = Uri.splitQueryString(realUrl.query) ?? {};
    if (null == params || params.isEmpty) {
      params = {};
    }
    params.addAll(urlParams);
    print("✌️ params = $params $_routerMap");

    if (_routerMap.containsKey(urlWithoutParams)) {
      WidgetBuilder pageBuilder = _routerMap[urlWithoutParams];
      print("🐳 is Flutter $urlWithoutParams");
      PinkNavigatorWrapper.push(
          pageBuilder, RouteSettings(name: completeUrlStr, arguments: params));
    } else {
      print("🏌is Native $urlWithoutParams");
      return _channel.open(url, params);
    }
  }

  static String _getUrlStrWithoutParams(Uri uri) {
    if (null == uri) {
      return "";
    }
    return uri.scheme + "://" + uri.host + uri.path;
  }

  static String _autoCompleteUrl(String urlStr) {
    if (urlStr.startsWith("/")) {
      urlStr = urlStr.substring(1);
    }
    if (!urlStr.contains("://")) {
      urlStr = "$_scheme://$urlStr";
    }
    return urlStr;
  }
}
