import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';
import 'pink_navigator_wrapper.dart';
import 'pink_util.dart';

typedef MethodBlock<R> = R Function(Map<String, dynamic> params);

class PinkIntent {
  WidgetBuilder _builder;
  MethodBlock _methodBlock;
  Uri _uri;
  String _urlStr;

  PinkIntent.uri(String uriStr, {MethodBlock block, WidgetBuilder builder}) {
    _urlStr = PinkUtil.getUrlKey(uriStr);
    _uri = Uri.parse(_urlStr);
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
