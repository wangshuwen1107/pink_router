import 'package:flutter/material.dart';
import '../navigator_default.dart';
import '../navigator_page_route.dart';
import '../pink_router_wrapper.dart';

class PinkNavigatorObserver extends NavigatorObserver {
  final pageRoutes = <PageRoute>[
    PinkPageRoute(
        builder: (context) => const NavigatorDefault(),
        settings: RouteSettings(name: "/", arguments: null))
  ];

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    // if (!(route is PinkPageRoute)) {
    //   return;
    // }
    String url = route.settings.name;
    print("🐳 Flutter didPush url=$url params=${route.settings.arguments}");
    if (url == "/") {
      return;
    }
    pageRoutes.add(route);

    PinkRouterWrapper.pageObserverSendChannel.didAppear(route.settings);
    PinkRouterWrapper.pageObserverSendChannel
        .didDisappear(previousRoute.settings);
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
      print(
          "🐳 Flutter didPop url=${route.settings.name} previousRoute=${previousRoute.settings.name}");
      pageRoutes.removeLast();
    PinkRouterWrapper.pageObserverSendChannel.didAppear(previousRoute.settings);
    PinkRouterWrapper.pageObserverSendChannel.destroy(route.settings);
  }
}
