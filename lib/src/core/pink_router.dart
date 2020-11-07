import 'package:pink_router/pink.dart';
import 'package:flutter/material.dart';
import 'pink_router_wrapper.dart';
import '../module/pink_module.dart';

class PinkRouter {
  static String get scheme => _scheme;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
  }

  static TransitionBuilder builder() {
    return PinkRouterWrapper.builder();
  }

  static void register(List<PinkModule> modules) {
    PinkRouterWrapper.register(modules);
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
