import 'package:flutter/material.dart';

mixin PageLifeCycleObserver {

  void create(RouteSettings routeSettings) {}

  void willAppear(RouteSettings routeSettings) {}

  void didAppear(RouteSettings routeSettings) {}

  void willDisappear(RouteSettings routeSettings) {}

  void didDisappear(RouteSettings routeSettings) {}

  void destroy(RouteSettings routeSettings) {}

}
