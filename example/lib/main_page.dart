import 'package:flutter/material.dart';
import 'package:pink_router/pink.dart';

class MainPage extends StatefulWidget {
  @override
  _MainPageState createState() => _MainPageState();
}

class _MainPageState extends State<MainPage> with PageLifecycleAble {
  @override
  void create(RouteSettings routeSettings) {
    print("MainPage ===create=== $routeSettings");
  }

  @override
  void willAppear(RouteSettings routeSettings) {
    print("MainPage ===willAppear=== $routeSettings");
  }

  @override
  void didAppear(RouteSettings routeSettings) {
    print("MainPage ===didAppear=== $routeSettings");
  }

  @override
  void willDisappear(RouteSettings routeSettings) {
    print("MainPage ===willDisappear=== $routeSettings");
  }

  @override
  void didDisappear(RouteSettings routeSettings) {
    print("MainPage ===didDisappear=== $routeSettings");
  }

  @override
  void destroy(RouteSettings routeSettings) {
    print("MainPage ===destroy=== $routeSettings");
  }

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    print("main page build called ");
    return Scaffold(
      appBar: AppBar(
          title: Text(
            'Pink Router',
          ),
          leading: IconButton(
            icon: const Icon(Icons.arrow_back),
            onPressed: () {
              PinkRouter.pop();
            },
          )),
      body: Container(
        padding: EdgeInsets.only(left: 12),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter->Flutter(回传)",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushFlutterPageA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter->Flutter(方法)",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _invokeMethodA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter->Native(回参)",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushNativePageA(),
            ),
            MaterialButton(
              color: Colors.pink[100],
              child: Text(
                "Flutter->Native->Flutter",
                style: TextStyle(color: Colors.white),
              ),
              onPressed: () => _pushNativePageB(),
            )
          ],
        ),
      ),
    );
  }

  _pushFlutterPageA() {
    PinkRouter.push("/test/flutterA?title=Go").then((value) {
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
    }).catchError((e) {});
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
    PinkRouter.push("test/nativePageB?title=BBB").then((value) {});
  }
}
