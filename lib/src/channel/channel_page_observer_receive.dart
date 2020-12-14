import 'package:flutter/material.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'channel_proxy.dart';
import '../core/observer/navigator_page_observers.dart';

class PageObserverReceiveChannel {

  final ChannelProxy _channelProxy;

  PageObserverReceiveChannel(this._channelProxy) {
    _onCreate();
    _onWillAppear();
    _onDidAppear();
    _onWillDisappear();
    _onDidDisappear();
    _onDestroy();
  }


  _onCreate() {
    _channelProxy.registerMethodHandler("create", (args) {
      NavigatorPageObserverManager.create(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }

  _onWillAppear() {
    _channelProxy.registerMethodHandler("willAppear", (args) {
      NavigatorPageObserverManager.willAppear(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }

  _onDidAppear() {
    _channelProxy.registerMethodHandler("didAppear", (args) {
      NavigatorPageObserverManager.didAppear(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }


  _onWillDisappear() {
    _channelProxy.registerMethodHandler("willDisappear", (args) {
      NavigatorPageObserverManager.willDisappear(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }

  _onDidDisappear() {
    _channelProxy.registerMethodHandler("didDisappear", (args) {
      NavigatorPageObserverManager.didDisappear(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }

  _onDestroy() {
    _channelProxy.registerMethodHandler("destroy", (args) {
      NavigatorPageObserverManager.destroy(
          RouteSettings(name: args['url'], arguments: args['params']));
      return Future.value(true);
    });
  }

}
