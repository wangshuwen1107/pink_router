import 'channel_proxy.dart';

class SendChannel {
  final ChannelProxy _channelProxy;

  SendChannel(this._channelProxy);

  Future<T> registerToNative<T>(List<String> routerUrlList) {
    return _channelProxy
        .invokeMethod("registerRouter", routerUrlList);
  }

  Future<T> navigation<T>(String urlStr, Map<String, dynamic> allParams) {
    Map<String, dynamic> argsMap = {};
    if (null != allParams) {
      argsMap.addAll(argsMap);
    }
    argsMap['url'] = urlStr;
    return _channelProxy.invokeMethod("navigation", argsMap);
  }
}