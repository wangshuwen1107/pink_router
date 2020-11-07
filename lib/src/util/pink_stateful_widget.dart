import 'package:flutter/material.dart';

extension PinkStatefulWidget on StatefulWidget {

  /// Get widget state from the global key.
  T tryStateOf<T extends State<StatefulWidget>>() {
    if (this.key == null) {
      return null;
    }
    final key = this.key;
    if (key is GlobalKey<T>) {
      return key.currentState;
    }
    return null;
  }
}
