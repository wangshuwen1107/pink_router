import 'package:flutter/cupertino.dart';
import 'package:pink_router/pink.dart';

import '../util/pink_util.dart';
import 'pink_router_wrapper.dart';
import '../module/pink_module.dart';

class PinkRouter {
  static String get scheme => _scheme;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
  }

  static void register(List<PinkModule> modules) {
    PinkRouterWrapper.getInstance().register(modules);
  }

  static Future<dynamic> push(String url, {Map<String, dynamic> params}) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);

    WidgetBuilder widgetBuilder =
        PinkRouterWrapper.getInstance().getPage(urlStr);
    if (null != widgetBuilder) {
      print("🐳 Flutter page: $urlStr   params = $allParams");
      return PinkRouterWrapper.getInstance()
          .push(widgetBuilder, RouteSettings(name: urlStr, arguments: params));
    }
    print("💘 Native page: $urlStr  params = $allParams");
    return PinkRouterWrapper.getInstance().push2Native(url, params);
  }

  static Future<dynamic> call(String url, {Map<String, dynamic> params}) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);

    MethodBlock methodBlock = PinkRouterWrapper.getInstance().getMethod(urlStr);
    if (null != methodBlock) {
      print("🐳 Flutter method: $urlStr   params = $allParams");
      return methodBlock.call(params);
    }
    print("💘 Native method: $urlStr  params = $allParams");
    return PinkRouterWrapper.getInstance().call2Native(url, params);
  }
}
