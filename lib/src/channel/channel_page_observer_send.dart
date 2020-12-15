import 'package:flutter/material.dart';
import 'channel_proxy.dart';
import '../entity/pink_const.dart';

class PageObserverSendChannel {

  final ChannelProxy _channelProxy;

  PageObserverSendChannel(this._channelProxy);

  create(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("create", settings);
    }
  }

  willAppear(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("willAppear", settings);
    }
  }

  didAppear(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("didAppear", settings);
    }
  }

  willDisappear(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("willDisappear", settings);
    }
  }

  didDisappear(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("didDisappear", settings);
    }
  }

  destroy(RouteSettings settings) {
    if(settings.name != PinkConstant.DEFAULT_PAGE_NAME){
      _sendLifeCycle("destroy", settings);
    }
  }

  _sendLifeCycle(String lifeCycle, RouteSettings settings) {
    _channelProxy.invokeMethod(lifeCycle, {
      "url": settings.name,
      "params": settings.arguments,
    });
  }
}
