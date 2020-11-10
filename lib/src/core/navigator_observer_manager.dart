import 'package:flutter/material.dart';
import 'navigator_default.dart';
import 'navigator_page_route.dart';

class NavigatorObserverManager extends NavigatorObserver {
  final pageRoutes = <PinkPageRoute>[
    PinkPageRoute(
        builder: (context) => const NavigatorDefault(),
        settings: RouteSettings(name: "/", arguments: null))
  ];

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    if (route is PinkPageRoute) {
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
    if (route is PinkPageRoute) {
      print(
          "🐳 Flutter didPop url=${route.settings.name} params=${route.settings.arguments}");
      pageRoutes.removeLast();
    }
  }
}
