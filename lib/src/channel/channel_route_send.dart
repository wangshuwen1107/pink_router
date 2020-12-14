import 'channel_proxy.dart';

class RouteSendChannel {
  final ChannelProxy _channelProxy;

  RouteSendChannel(this._channelProxy);

  Future<T> registerToNative<T>(List<String> routerUrlList) {
    return _channelProxy.invokeMethod("registerRouter", routerUrlList);
  }

  Future<T> push<T>(String urlStr, Map<String, dynamic> allParams) {
    Map<String, dynamic> argsMap = {};
    if (null != allParams) {
      argsMap.addAll(argsMap);
    }
    argsMap['url'] = urlStr;
    argsMap['params'] = allParams;
    return _channelProxy.invokeMethod("push", argsMap);
  }

  Future<dynamic> pop<T extends Object>([T result]) {
    return _channelProxy.invokeMethod("pop", result);
  }

  Future<T> call<T>(String urlStr, Map<String, dynamic> allParams) {
    Map<String, dynamic> argsMap = {};
    if (null != allParams) {
      argsMap.addAll(argsMap);
    }
    argsMap['url'] = urlStr;
    return _channelProxy.invokeMethod("call", argsMap);
  }
}
