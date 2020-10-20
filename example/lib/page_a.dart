import 'package:flutter/material.dart';

class PageA extends StatefulWidget {
  @override
  _PageAState createState() => _PageAState();
}

class _PageAState extends State<PageA> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('PageA'),
        automaticallyImplyLeading: false,
        leading: IconButton(
            icon: Icon(Icons.arrow_back, size: 24),
            onPressed: () {

            }),
      ),
      body: Center(
        child: Text("Hello A"),
      ),
    );
  }
}
