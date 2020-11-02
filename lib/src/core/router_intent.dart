import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';
import '../util/pink_util.dart';
import '../pink_block_type.dart';
import 'navigator_wrapper.dart';

class RouterIntent {
  WidgetBuilder _builder;
  MethodBlock _methodBlock;
  String _urlStr;

  RouterIntent.uri(String uriStr, {MethodBlock block, WidgetBuilder builder}) {
    _urlStr = PinkUtil.getUrlKey(uriStr);
    _methodBlock = block;
    _builder = builder;
    assert(null == _methodBlock || null == _builder,
        "Only one type method or page");
  }

  Future<dynamic> start(Map<String, dynamic> params) async {
    if (_builder != null) {
      return PinkNavigatorWrapper.push(
          _builder, RouteSettings(name: _urlStr, arguments: params));
    } else if (_methodBlock != null) {
      return await _methodBlock(params);
    } else {
      return Future.value(false);
    }
  }
}
