import 'channel_proxy.dart';


class ReceiveChannel {
  final ChannelProxy _channelProxy;

  ReceiveChannel(this._channelProxy) {
    onNavigation();
  }

  onNavigation() {
    _channelProxy.registerMethodHandler("navigation", (args) {
      String url = args["url"];
      Map params = args["params"];
      Map<String, dynamic> stringParams = {};
      params.forEach((key, value) {
        if (key is String) {
          stringParams[key] = value;
        }
      });
     // return routinte.push(url, params: stringParams);
    });
  }
}
