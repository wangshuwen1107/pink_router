import 'package:flutter/material.dart';

class PinkPageRoute<T> extends MaterialPageRoute<T> {
  final bool isNested;

  PinkPageRoute(
      {@required WidgetBuilder builder,
      RouteSettings settings,
      bool maintainState = true,
      bool fullscreenDialog = false,
      this.isNested})
      : super(
            builder: builder,
            settings: settings,
            maintainState: maintainState,
            fullscreenDialog: fullscreenDialog);

  @override
  Widget buildTransitions(
    BuildContext context,
    Animation<double> animation,
    Animation<double> secondaryAnimation,
    Widget child,
  ) {
    if (isNested) {
      return super.buildTransitions(
        context,
        animation,
        secondaryAnimation,
        child,
      );
    }
    return child;
  }
}
