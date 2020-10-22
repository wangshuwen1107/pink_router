import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class PinkNavigatorWrapper {

  static GlobalKey<NavigatorState> _key;

  static GlobalKey<NavigatorState> get navigatorKey =>
      _key ??= GlobalKey<NavigatorState>();

  static NavigatorState get _navigator => navigatorKey.currentState;

  static Future push(WidgetBuilder pageBuilder, RouteSettings routeSettings) {
    var pageRoute = MaterialPageRoute(builder: pageBuilder,settings: routeSettings);
    _navigator.push(pageRoute).catchError((error) {});
    return Future.value(true);
  }


}
