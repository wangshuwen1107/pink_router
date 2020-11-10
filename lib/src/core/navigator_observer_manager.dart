import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'navigator_default.dart';

class NavigatorObserverManager extends NavigatorObserver {
  final pageRoutes = <MaterialPageRoute>[
    MaterialPageRoute(
        builder: (context) => const NavigatorDefault(),
        settings: RouteSettings(name: "/", arguments: null))
  ];

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    if (route is MaterialPageRoute) {
      String url = route.settings.name;
      print("🐳 Flutter didPush url=$url params=${route.settings.arguments}");
      if (url == "/") {
        return;
      }
      pageRoutes.add(route);
    }
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
    if (route is MaterialPageRoute) {
      print("🐳 Flutter didPop url=${route.settings.name} params=${route.settings.arguments}");
      pageRoutes.removeLast();
    }
  }
}
