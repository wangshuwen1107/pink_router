import 'package:pink_router/pink.dart';
import 'package:flutter/material.dart';
import 'observer/navigator_page_observer.dart';
import 'pink_router_wrapper.dart';
import '../module/pink_module.dart';
import 'observer/navigator_page_observer_manager.dart';

class PinkRouter {
  static String get scheme => _scheme;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(null!=scheme && scheme.isNotEmpty);
    _scheme = scheme;
  }

  static void register(List<PinkModule> modules) {
    PinkRouterWrapper.register(modules);
  }

  static void registerPageObserver(PageLifeCycleObserver observer) {
    NavigatorPageObserverManager.registerPageObserver(observer);
  }

  static void unRegisterPageObserver(PageLifeCycleObserver observer) {
    NavigatorPageObserverManager.unRegisterPageObserver(observer);
  }

  static Future<dynamic> push(String url, {Map<String, dynamic> params}) {
    return PinkRouterWrapper.push(url, params);
  }

  static void pop<T extends Object>([T result]) {
    PinkRouterWrapper.pop(result);
  }

  static Future<dynamic> call(String url, {Map<String, dynamic> params}) {
    return PinkRouterWrapper.call(url, params);
  }
}
