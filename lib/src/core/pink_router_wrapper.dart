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

  WidgetBuilder getPage(String urlStr) {
    return _pageMap[urlStr];
  }

  MethodBlock getMethod(String urlStr) {
    return _methodMap[urlStr];
  }

  Future<T> push<T>(WidgetBuilder builder, RouteSettings routeSettings) {
    var pageRoute =
    MaterialPageRoute(builder: builder, settings: routeSettings);
    return _navigator.push(pageRoute).catchError((error) {});
  }


  Future<T> push2Native<T>(String url, Map<String, dynamic> params) {
    return sendChannel.push(url, params);
  }


  Future<T> call2Native<T>(String url, Map<String, dynamic> params) {
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
