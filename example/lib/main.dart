import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';
import 'page_a.dart';

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
    PinkRouter.init("pink");
    PinkRouter.register(
      pageMap: {
        "test/pageA": (context) => PageA(),
      },
      methodMap: {
        "test/methodA": (params) => _methodA(params),
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      navigatorKey: PinkNavigatorWrapper.navigatorKey,
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Pink Router'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              MaterialButton(
                color: Colors.redAccent,
                child: Text("Route Flutter PageA"),
                onPressed: () {
                  PinkRouter.open("test/pageA?title=Go");
                },
              )
            ],
          ),
        ),
      ),
    );
  }

  String _methodA(params) {
    print("Method A is called");
    return "methodA";
  }
}
