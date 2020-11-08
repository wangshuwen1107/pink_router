import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'channel_proxy.dart';

class ReceiveChannel {
  final ChannelProxy _channelProxy;

  ReceiveChannel(this._channelProxy) {
    onPush();
    onPop();
  }

  onPush() {
    _channelProxy.registerMethodHandler("push", (args) {
      String url = args["url"];
      Map params = args["params"];
      Map<String, dynamic> stringParams = {};
      params.forEach((key, value) {
        if (key is String) {
          stringParams[key] = value;
        }
      });
      return PinkRouterWrapper.navigatorProxyState.push(url, stringParams);
    });
  }

  onPop() {
    _channelProxy.registerMethodHandler("pop", (args) {
      bool isBackPress = args['isBackPress'] ?? false;
      dynamic result = args['result'];
      return PinkRouterWrapper.navigatorProxyState
          .maybePop(isBackPress: isBackPress, result: result);
    });
  }
}
