import 'package:flutter/material.dart';
import '../util/pink_util.dart';
import '../core/router_intent.dart';
import 'pink_module.dart';
import '../pink_block_type.dart';

class ModuleManager {
  static ModuleManager _instance;

  ModuleManager._internal();

  factory ModuleManager.getInstance() => _getInstance();

  static _getInstance() {
    if (_instance == null) {
      _instance = ModuleManager._internal();
    }
    return _instance;
  }

  Map<String, RouterIntent> _routerMap = {};

  List<String> get routerList => _routerList;

  List<String> _routerList = [];

  void register(PinkModule module) {
    module.onPageRegister()?.forEach((key, value) {
      _addRoute2Map(key, builder: value);
    });
    module.onMethodRegister()?.forEach((key, value) {
      _addRoute2Map(key, block: value);
    });
  }

  RouterIntent getIntent(String urlStr) {
    return _routerMap[urlStr];
  }

  void _addRoute2Map(String url, {WidgetBuilder builder, MethodBlock block}) {
    String key = PinkUtil.getUrlKey(url);
    Uri uri = Uri.parse(key);
    assert(PinkUtil.checkUri(uri), "Route Register Error scheme://host/path");
    _routerMap[key] = RouterIntent.uri(key, builder: builder, block: block);
    _routerList.add(key);
  }

}
