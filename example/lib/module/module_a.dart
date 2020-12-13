import 'package:pink_router/pink.dart';
import '../page_a.dart';
import '../page_b.dart';
import 'package:flutter/material.dart';
import '../main_page.dart';

class ModuleA with PinkModule, LifeCycleObserver {

  @override
  Map<String, MethodBlock> onMethodRegister() {
    return {
      "test/methodA": (params) => _methodA(params),
    };
  }

  @override
  Map<String, WidgetBuilder> onPageRegister() {
    return {
      "test/main": (context) => MainPage(),
      "test/flutterA": (context) => PageA(),
      "test/flutterB": (context) => PageB(),
    };
  }

  String _methodA(params) {
    print("Method A is called $params");
    return "methodA";
  }

  @override
  void create(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} create");
  }

  @override
  void willAppear(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} willAppear");
  }

  @override
  void didAppear(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} didAppear");
  }


  @override
  void willDisappear(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} willDisappear");
  }


  @override
  void didDisappear(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} didDisappear");
  }


  @override
  void destroy(RouteSettings routeSettings) {
    print(
        "LifeCycle ${routeSettings.name} arguments=${routeSettings.arguments} destroy");
  }



}
