import 'package:flutter/material.dart';
import '../navigator_default.dart';
import '../navigator_page_route.dart';
import '../pink_router_wrapper.dart';

class NavigatorObserverManager extends NavigatorObserver {
  final pageRoutes = <PinkPageRoute>[
    PinkPageRoute(
        builder: (context) => const NavigatorDefault(),
        settings: RouteSettings(name: "/", arguments: null))
  ];

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    if (!(route is PinkPageRoute)) {
      return;
    }
    String url = route.settings.name;
    print("🐳 Flutter didPush url=$url params=${route.settings.arguments}");
    if (url == "/") {
      return;
    }
    previousRoute = pageRoutes.last;
    pageRoutes.add(route);
    PinkRouterWrapper.observerList.forEach((observer) {
      observer.didAppear(route.settings);
      if (previousRoute.settings.name != "/") {
        observer.didDisappear(previousRoute.settings);
      }
    });
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
    if (route is PinkPageRoute) {
      print(
          "🐳 Flutter didPop url=${route.settings.name} previousRoute=${previousRoute.settings.name}");
      pageRoutes.removeLast();

      PinkRouterWrapper.observerList.forEach((observer) {
        observer.destroy(route.settings);
        if (previousRoute is PinkPageRoute) {
           observer.didAppear(previousRoute.settings);
        }
      });
    }
  }
}
