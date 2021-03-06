import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';

class PageB extends StatefulWidget {
  @override
  _PageBState createState() => _PageBState();
}

class _PageBState extends State<PageB> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var arguments = ModalRoute.of(context).settings.arguments as Map;
    return Scaffold(
      appBar: AppBar(
          title: Text('FlutterPageB'),
          automaticallyImplyLeading: false,
          leading: IconButton(
            icon: const Icon(Icons.arrow_back),
            onPressed: () {
              PinkRouter.pop({"flutterPageB": "resultOk"});
            },
          )),
      body: Center(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.asset(
              "images/flutter_logo.png",
              width: 60,
              height: 60,
            ),
            Text(
              "Arguments : $arguments",
              style: TextStyle(fontSize: 14),
            ),
          ],
        ),
      ),
    );
  }

  @override
  void dispose() {
    super.dispose();
  }
}
