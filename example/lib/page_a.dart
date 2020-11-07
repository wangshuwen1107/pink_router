import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';

class PageA extends StatefulWidget {
  @override
  _PageAState createState() => _PageAState();
}

class _PageAState extends State<PageA> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var arguments = ModalRoute.of(context).settings.arguments as Map;
    return WillPopScope(
      child: Scaffold(
        appBar: AppBar(
            title: Text('FlutterPageA'),
            automaticallyImplyLeading: false,
            leading: IconButton(
              icon: const Icon(Icons.arrow_back),
              onPressed: () {
                PinkRouter.pop({"appbarBack": "flutter a back"});
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
      ),
      onWillPop: () async {
        PinkRouter.pop("onWillPop");
        print("Page a BACK CALLED  ----");
        return false;
      },
    );
  }
}
