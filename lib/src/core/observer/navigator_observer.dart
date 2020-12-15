import 'package:flutter/material.dart';
import '../navigator_default.dart';
import '../navigator_page_route.dart';
import '../pink_router_wrapper.dart';
import '../../entity/pink_const.dart';

class PinkNavigatorObserver extends NavigatorObserver {
  final pageRoutes = <PinkPageRoute>[
    PinkPageRoute(
        builder: (context) => const NavigatorDefault(),
        settings: RouteSettings(
            name: PinkConstant.DEFAULT_PAGE_NAME, arguments: null))
  ];

  @override
  void didPush(Route route, Route previousRoute) {
    super.didPush(route, previousRoute);
    if (!(route is PinkPageRoute)) {
      return;
    }
    String url = route.settings.name;
    if (null == url || url.isEmpty || url == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    print("🐳 Flutter didPush url=$url params=${route.settings.arguments}");
    pageRoutes.add(route);
    PinkRouterWrapper.pageObserverSendChannel.didAppear(route.settings);
    PinkRouterWrapper.pageObserverSendChannel
        .didDisappear(previousRoute.settings);
  }

  @override
  void didPop(Route route, Route previousRoute) {
    super.didPop(route, previousRoute);
    if (!(route is PinkPageRoute)) {
      return;
    }
    String url = route.settings.name;
    if (null == url || url.isEmpty || url == PinkConstant.DEFAULT_PAGE_NAME) {
      return;
    }
    print(
        "🐳 Flutter didPop url=${route.settings.name} previousRoute=${previousRoute.settings.name}");
    pageRoutes.removeLast();
    PinkRouterWrapper.pageObserverSendChannel.didAppear(previousRoute.settings);
    PinkRouterWrapper.pageObserverSendChannel.destroy(route.settings);
  }
}
