import 'package:flutter/material.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'observer/navigator_observer.dart';
import '../util/pink_util.dart';
import '../util/pink_stateful_widget.dart';
import 'navigator_page_route.dart';

class NavigatorProxyWidget extends StatefulWidget {
  const NavigatorProxyWidget(
      {Key key, this.navigator, this.pinkNavigatorObserver})
      : super(key: key);

  @override
  NavigatorProxyWidgetState createState() => NavigatorProxyWidgetState();

  final Navigator navigator;

  final PinkNavigatorObserver pinkNavigatorObserver;
}

class NavigatorProxyWidgetState extends State<NavigatorProxyWidget> {
  List<PageRoute> get pageRouteList => widget.pinkNavigatorObserver.pageRoutes;

  @override
  Widget build(BuildContext context) {
    return widget.navigator;
  }

  Future<dynamic> push(String url, Map<String, dynamic> params) {
    final navigatorState = widget.navigator.tryStateOf<NavigatorState>();
    bool isNested = false;
    if (null != params) {
      isNested = params.remove("isNested") ?? false;
    }
    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);

    WidgetBuilder builder = PinkRouterWrapper.pageBuilder[urlStr];

    if (null != builder) {
      RouteSettings settings =
          RouteSettings(name: urlStr, arguments: allParams);
      PinkPageRoute pageRoute = PinkPageRoute(
          isNested: isNested, builder: builder, settings: settings);
      PageRoute previousPage = pageRouteList.last;

      PinkRouterWrapper.pageObserverSendChannel.create(settings);

      PinkRouterWrapper.pageObserverSendChannel
          .willDisappear(previousPage.settings);

      PinkRouterWrapper.pageObserverSendChannel.willAppear(settings);

      return navigatorState.push(pageRoute);
    }
    return Future.value(false);
  }

  Future<bool> maybePop<T extends Object>({bool isBackPress, T result}) async {
    PageRoute lastPage = pageRouteList.last;
    PageRoute willLastPage;
    if (pageRouteList.length >= 2) {
      willLastPage = pageRouteList[pageRouteList.length - 2];
    }
    var isWillPop = false;
    if (isBackPress) {
      isWillPop = await lastPage.willPop() == RoutePopDisposition.pop;
    }
    bool needPop = !isBackPress || isWillPop;
    if (needPop) {
      final navigatorState = widget.navigator.tryStateOf<NavigatorState>();
      PinkRouterWrapper.pageObserverSendChannel
          .willDisappear(lastPage.settings);
      if (null != willLastPage) {
        PinkRouterWrapper.pageObserverSendChannel
            .willAppear(willLastPage.settings);
      }
      PinkRouterWrapper.pageObserverSendChannel.didDisappear(lastPage.settings);
      if (null != willLastPage) {
        PinkRouterWrapper.pageObserverSendChannel
            .didAppear(willLastPage.settings);
      }
      navigatorState?.pop(result);
    }
    print("maybePop isBack=$isBackPress result=$result canPop=$needPop ");
    return needPop;
  }
}
