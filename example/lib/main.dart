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
    PinkRouter.init({
      "test/pageA": (context) => PageA(),
      "test/pageB": (context) => PageA()
    }, scheme: "pink");
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Pink Router'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              MaterialButton(
                child: Text("Route Flutter PageA"),
                onPressed: () {
                  PinkRouter.page("scheme://test/pageA?title=Go");
                },
              )
            ],
          ),
        ),
      ),
    );
  }
}
