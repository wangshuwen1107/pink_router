import 'dart:io';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pink_router/pink.dart';
import 'module/module_a.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp>  {
  @override
  void initState() {
    super.initState();
    print("Flutter app is create ...");
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);

    PinkRouter.init("pink");
    PinkRouter.register([ModuleA()]);
    PinkRouter.addLifeCycleObserver(ModuleA());
  }

  @override
  Widget build(BuildContext context) {
    return PinkApp(
      theme: new ThemeData(
        primaryColor: Colors.pink[100],
        accentColor: Colors.pink[100],
      ),
    );
  }
  
}
