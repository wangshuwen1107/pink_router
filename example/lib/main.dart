import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pink_router/pink.dart';
import 'module_a.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);
    PinkRouter.init("pink");
    PinkRouter.register([ModuleA()]);
  }

  @override
  Widget build(BuildContext context) {
    return PinkApp(
        theme: new ThemeData(
      primaryColor: Colors.pink[100],
      //Changing this will change the color of the TabBar
      accentColor: Colors.pink[100],
    ));
  }
}
