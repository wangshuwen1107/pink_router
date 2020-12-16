import 'package:flutter/widgets.dart';

extension PinkBuildContext on BuildContext {

  T stateOf<T extends State<StatefulWidget>>() {
    final state = findAncestorStateOfType<T>();
    if (state != null && state is T) {
      return state;
    }
    throw Exception('${state.runtimeType} is not a $T');
  }

  T tryStateOf<T extends State<StatefulWidget>>() {
    final state = findAncestorStateOfType<T>();
    if (state != null && state is T) {
      return state;
    }
    return null;
  }
}
