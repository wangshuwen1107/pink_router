import 'package:flutter/material.dart';
import '../pink_block_type.dart';

mixin PinkModule {

  Map<String, WidgetBuilder> onPageRegister();

  Map<String, MethodBlock> onMethodRegister();
}
