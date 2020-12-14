import 'package:flutter/material.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'channel_proxy.dart';
import '../core/observer/navigator_page_observers.dart';

class PageObserverSendChannel {

  final ChannelProxy _channelProxy;

  PageObserverSendChannel(this._channelProxy);

  create(RouteSettings settings) {
    _sendLifeCycle("create", settings);
  }

  willAppear(RouteSettings settings) {
    _sendLifeCycle("willAppear", settings);
  }

  didAppear(RouteSettings settings) {
    _sendLifeCycle("didAppear", settings);
  }

  willDisappear(RouteSettings settings) {
    _sendLifeCycle("willDisappear", settings);
  }

  didDisappear(RouteSettings settings) {
    _sendLifeCycle("didDisappear", settings);
  }

  destroy(RouteSettings settings) {
    _sendLifeCycle("destroy", settings);
  }

  _sendLifeCycle(String lifeCycle, RouteSettings settings) {
    _channelProxy.invokeMethod(lifeCycle, {
      "url": settings.name,
      "params": settings.arguments,
    });
  }
}
