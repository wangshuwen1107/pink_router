import 'package:pink_router/pink.dart';
import 'page_a.dart';
import 'page_b.dart';
import 'package:flutter/material.dart';
import 'main_page.dart';

class ModuleA with PinkModule {

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

}
