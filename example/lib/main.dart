import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:pink_router/pink.dart';
import 'module_a.dart';
import 'main_page.dart';

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
    print("Flutter app is create ...");
    SystemUiOverlayStyle systemUiOverlayStyle =
        SystemUiOverlayStyle(statusBarColor: Colors.transparent);
    SystemChrome.setSystemUIOverlayStyle(systemUiOverlayStyle);

    PinkRouter.init("pink");
    PinkRouter.register([ModuleA()]);
  }

  @override
  Widget build(BuildContext context) {
    return PinkApp(
        home: MainPage(),
        theme: new ThemeData(
          primaryColor: Colors.pink[100],
          //Changing this will change the color of the TabBar
          accentColor: Colors.pink[100],
        ));
  }
}
