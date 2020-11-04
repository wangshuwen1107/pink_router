import 'package:flutter/services.dart';

typedef MethodHandler = Future<dynamic> Function(dynamic);

class ChannelProxy {
  MethodChannel _methodChannel;
  EventChannel _eventChannel;

  Map<String, MethodHandler> _methodHandlerMap = {};

  ChannelProxy(String name) {
    _methodChannel = MethodChannel("${name}_method_channel");
    _eventChannel = EventChannel("${name}_event_channel");
    _methodChannel.setMethodCallHandler((call) {
      String methodName = call.method;
      if (methodName.isEmpty) {
        return Future.value(false);
      }
      print("🐳 Native->Flutter[Method] name=$methodName params=${call.arguments}");
      MethodHandler handler = _methodHandlerMap[methodName];
      if (null == handler) {
        return Future.value(false);
      }
      return handler.call(call.arguments);
    });
    _eventChannel.receiveBroadcastStream((args) {});
  }

  void registerMethodHandler(String name, MethodHandler handler) {
    _methodHandlerMap[name] = handler;
  }

  Future<T> invokeMethod<T>(String methodName, dynamic args) {
    return _methodChannel.invokeMethod(methodName, args);
  }
}
