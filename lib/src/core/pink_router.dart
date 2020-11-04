import 'package:pink_router/pink.dart';

import 'pink_router_wrapper.dart';
import '../module/pink_module.dart';

class PinkRouter {
  static String get scheme => _scheme;

  static String _scheme = "pink";

  static void init(String scheme) {
    assert(scheme.isNotEmpty);
    _scheme = scheme;
  }

  static void register(List<PinkModule> modules) {
    PinkRouterWrapper.getInstance().register(modules);
  }

  static Future<dynamic> push(String url, {Map<String, dynamic> params}) {
    return PinkRouterWrapper.getInstance().push(url, params);
  }

  static void pop<T extends Object>([T result]) {
    PinkRouterWrapper.getInstance().pop(result);
  }

  static Future<dynamic> call(String url, {Map<String, dynamic> params}) {
    return PinkRouterWrapper.getInstance().call(url, params);
  }
}
