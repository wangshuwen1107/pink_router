import 'package:flutter/material.dart';
import '../util/pink_util.dart';
import '../module/pink_module.dart';
import '../pink_block_type.dart';
import '../channel/channel_proxy.dart';
import '../channel/channel_send.dart';
import '../channel/channel_receive.dart';

class PinkRouterWrapper {
  static PinkRouterWrapper _instance;

  static GlobalKey<NavigatorState> _key;

  static GlobalKey<NavigatorState> get navigatorKey =>
      _key ??= GlobalKey<NavigatorState>();

  static NavigatorState get _navigator => navigatorKey.currentState;

  Map<String, WidgetBuilder> _pageMap = {};
  Map<String, MethodBlock> _methodMap = {};

  List<String> _routerList = [];

  ChannelProxy _channelProxy;

  SendChannel sendChannel;

  ReceiveChannel receiveChannel;

  Map<String, WidgetBuilder> get routers => _pageMap;

  PinkRouterWrapper._internal() {
    _channelProxy = ChannelProxy('main');
    sendChannel = SendChannel(_channelProxy);
    receiveChannel = ReceiveChannel(_channelProxy);
  }

  factory PinkRouterWrapper.getInstance() => _getInstance();

  static _getInstance() {
    if (_instance == null) {
      _instance = PinkRouterWrapper._internal();
    }
    return _instance;
  }

  void register(List<PinkModule> modules) {
    modules.forEach((module) {
      module.onPageRegister()?.forEach((key, value) {
        _addRoute2Map(key, builder: value);
      });
      module.onMethodRegister()?.forEach((key, value) {
        _addRoute2Map(key, block: value);
      });
    });
    sendChannel
        .registerToNative(_routerList)
        .then((value) {})
        .catchError((e) {});
  }

  Future<dynamic> onPush(String url, Map<String, dynamic> params) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);
    WidgetBuilder builder = _pageMap[urlStr];
    if (null != builder) {
      var pageRoute = MaterialPageRoute(
          builder: builder,
          settings: RouteSettings(name: urlStr, arguments: allParams));
      return _navigator.push(pageRoute).catchError((error) {});
    }
    return Future.value(false);
  }

  void onPop<T extends Object>([T result]) {
    _navigator.pop(result);
  }

  Future<T> push<T>(String url, Map<String, dynamic> params) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);
    print("参数 $allParams");
    return sendChannel.push(urlStr, allParams);
  }

  void pop<T extends Object>([T result]) {
    _navigator.pop(result);
    sendChannel.pop(result);
  }

  Future<T> call<T>(String url, Map<String, dynamic> params) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);
    MethodBlock methodBlock = _methodMap[urlStr];
    if (null != methodBlock) {
      print("🐳 Flutter method: $urlStr   params = $allParams");
      return methodBlock.call(params);
    }
    print("💘 Native method: $urlStr  params = $allParams");
    return sendChannel.call(url, params);
  }

  void _addRoute2Map(String url, {WidgetBuilder builder, MethodBlock block}) {
    String key = PinkUtil.getUrlKey(url);
    Uri uri = Uri.parse(key);
    assert(PinkUtil.checkUri(uri), "Route Register Error scheme://host/path");
    if (null != builder) {
      _pageMap[key] = builder;
    } else if (null != block) {
      _methodMap[key] = block;
    }
    _routerList.add(key);
  }
}
