import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';

class MainPage extends StatefulWidget {
  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(
          'Pink Router',
        ),
      ),
      body: Container(
        padding: EdgeInsets.only(left: 12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter PageA",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushFlutterPageA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter MethodA",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _invokeMethodA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Native PageA",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushNativePageA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Native PageB",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushNativePageA(),
            )
          ],
        ),
      ),
    );
  }

  _pushFlutterPageA() {
    PinkRouter.push("test/pageA?title=Go").then((value) {
      showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) {
            return AlertDialog(
              title: Text("PageA Result"),
              content: Text("$value"),
              actions: <Widget>[
                FlatButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text("确定"))
              ],
            );
          });
    });
  }

  _invokeMethodA() {
    PinkRouter.push("test/methodA?title=Go").then((value) {
      showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) {
            return AlertDialog(
              title: Text("MethodA Result"),
              content: Text("$value"),
              actions: <Widget>[
                FlatButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text("确定"))
              ],
            );
          });
    });
  }

  _pushNativePageA() {
    PinkRouter.push("test/nativePageA?title=AAA").then((value) {
      showDialog(
          context: context,
          barrierDismissible: false,
          builder: (context) {
            return AlertDialog(
              title: Text("NativePageA Result"),
              content: Text("$value"),
              actions: <Widget>[
                FlatButton(
                    onPressed: () {
                      Navigator.of(context).pop();
                    },
                    child: Text("确定"))
              ],
            );
          });
    });
  }


  _pushNativePageB() {
    PinkRouter.push("test/nativePageB?title=BBB")
        .then((value) {
    });
  }

}
