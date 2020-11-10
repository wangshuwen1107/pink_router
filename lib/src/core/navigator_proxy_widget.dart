import 'package:flutter/material.dart';
import 'package:pink_router/src/core/pink_router_wrapper.dart';
import 'navigator_observer_manager.dart';
import '../util/pink_util.dart';
import '../util/pink_stateful_widget.dart';

class NavigatorProxyWidget extends StatefulWidget {
  const NavigatorProxyWidget({Key key, this.navigator, this.pinkPageObserver})
      : super(key: key);

  @override
  NavigatorProxyWidgetState createState() => NavigatorProxyWidgetState();

  final Navigator navigator;

  final NavigatorObserverManager pinkPageObserver;
}

class NavigatorProxyWidgetState extends State<NavigatorProxyWidget> {
  @override
  Widget build(BuildContext context) {
    return widget.navigator;
  }

  Future<dynamic> push(String url, Map<String, dynamic> params) {
    final navigatorState = widget.navigator.tryStateOf<NavigatorState>();

    String urlStr = PinkUtil.getUrlKey(url);
    String completeUrlStr = PinkUtil.autoCompleteUrl(url);
    var allParams = PinkUtil.mergeParams(Uri.parse(completeUrlStr).query,
        extraParams: params);

    WidgetBuilder builder = PinkRouterWrapper.pageBuilder[urlStr];

    if (null != builder) {
      var pageRoute = MaterialPageRoute(
          builder: builder,
          settings: RouteSettings(name: urlStr, arguments: allParams));
      return navigatorState.push(pageRoute).catchError((error) {});
    }
    return Future.value(false);
  }

  Future<bool> maybePop<T extends Object>({bool isBackPress, T result}) async {
    print("maybePop is called isBackPress=$isBackPress result=$result");
    MaterialPageRoute pageRoute = widget.pinkPageObserver.pageRoutes.last;
    var isWillPop = false;
    if (isBackPress) {
      isWillPop = await pageRoute.willPop() == RoutePopDisposition.pop;
    }
    bool needPop = !isBackPress || isWillPop;
    if (needPop) {
      final navigatorState = widget.navigator.tryStateOf<NavigatorState>();
      navigatorState?.pop(pageRoute);
    }
    print("maybePop is end isBackPress=$isBackPress result=$result "
        "return=$needPop ");
    return needPop;
  }
}
