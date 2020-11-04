import 'package:pink_router/src/core/pink_router_wrapper.dart';

import 'channel_proxy.dart';
import '../core/pink_router.dart';

class ReceiveChannel {
  final ChannelProxy _channelProxy;

  ReceiveChannel(this._channelProxy) {
    onPush();
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
      return PinkRouterWrapper.getInstance().onPush(url, stringParams);
    });
  }
}
