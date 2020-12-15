import 'package:flutter/material.dart';
import 'package:pink_router/src/core/observer/navigator_observer.dart';
import '../util/pink_util.dart';
import '../module/pink_module.dart';
import '../pink_block_type.dart';
import '../channel/channel_proxy.dart';
import '../channel/channel_route_send.dart';
import '../channel/channel_route_receive.dart';
import '../channel/channel_page_observer_receive.dart';
import '../channel/channel_page_observer_send.dart';
import 'navigator_proxy_widget.dart';
import 'observer/navigator_page_observer.dart';
import 'observer/navigator_page_observer_manager.dart';
import '../entity/pink_const.dart';

class PinkRouterWrapper {
  static Map<String, WidgetBuilder> _pageMap = {};
  static Map<String, MethodBlock> _methodMap = {};

  static List<String> _routerList = [];

  static ChannelProxy _channelProxy;

  static RouteSendChannel routeSendChannel;

  static RouteReceiveChannel routeReceiveChannel;

  static PageObserverReceiveChannel pageObserverReceiveChannel;

  static PageObserverSendChannel pageObserverSendChannel;

  static Map<String, WidgetBuilder> get pageBuilder => _pageMap;

  static NavigatorProxyWidgetState get navigatorProxyState =>
      _navigatorProxyKey?.currentState;

  static GlobalKey<NavigatorProxyWidgetState> _navigatorProxyKey;

  static PinkNavigatorObserver _pinkNavigatorObserver;

  static TransitionBuilder builder() {
    if (null == _channelProxy) {
      _channelProxy = ChannelProxy(PinkConstant.CHANNEL_NAME);
      routeSendChannel = RouteSendChannel(_channelProxy);
      routeReceiveChannel = RouteReceiveChannel(_channelProxy);

      pageObserverReceiveChannel = PageObserverReceiveChannel(_channelProxy);
      pageObserverSendChannel = PageObserverSendChannel(_channelProxy);
      routeSendChannel
          .registerToNative(_routerList)
          .then((value) {})
          .catchError((e) {});
      _pinkNavigatorObserver = PinkNavigatorObserver();
    }
    return (context, child) {
      final navigator = child is Navigator ? child : null;
      if (!navigator.observers.contains(_pinkNavigatorObserver)) {
        navigator.observers.add(_pinkNavigatorObserver);
      }
      return NavigatorProxyWidget(
        key: _navigatorProxyKey ??= GlobalKey<NavigatorProxyWidgetState>(),
        navigator: navigator,
        pinkNavigatorObserver: _pinkNavigatorObserver,
      );
    };
  }

  static void register(List<PinkModule> modules) {
    modules.forEach((module) {
      module.onPageRegister()?.forEach((key, value) {
        _addRoute2Map(key, builder: value);
      });
      module.onMethodRegister()?.forEach((key, value) {
        _addRoute2Map(key, block: value);
      });
    });
  }

  static void addLifeCycleObserver(PageLifeCycleObserver observer){
    NavigatorPageObserverManager.addLifeCycleObserver(observer);
  }

  static Future<T> push<T>(String url, Map<String, dynamic> params) {
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);
    return routeSendChannel.push(urlStr, allParams);
  }

  static void pop<T extends Object>([T result]) {
    routeSendChannel.pop(result);
  }

  static Future<T> call<T>(String url, Map<String, dynamic> params) {
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
    return routeSendChannel.call(url, params);
  }

  static void _addRoute2Map(String url,
      {WidgetBuilder builder, MethodBlock block}) {
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
