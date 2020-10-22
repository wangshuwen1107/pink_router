import 'package:flutter/material.dart';

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
    print("args =$arguments");
    return Scaffold(
      appBar: AppBar(
        title: Text('PageA'),
        automaticallyImplyLeading: false,
        leading: IconButton(
            icon: Icon(Icons.arrow_back, size: 24), onPressed: () {}),
      ),
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
              "Arguments : ${arguments}",
              style: TextStyle(fontSize: 14),
            )
          ],
        ),
      ),
    );
  }
}
