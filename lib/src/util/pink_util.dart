import '../core/pink_router.dart';

class PinkUtil {
  /// 获取uri除query的短链
  static String getUrlStrWithoutParams(Uri uri) {
    if (null == uri) {
      return "";
    }
    return uri.scheme + "://" + uri.host + uri.path;
  }

  /// 自动填充 scheme 去除多余的'/'
  static String autoCompleteUrl(String urlStr) {
    if (urlStr.startsWith("/")) {
      urlStr = urlStr.substring(1);
    }
    if (!urlStr.contains("://")) {
      urlStr = "${PinkRouter.scheme}://$urlStr";
    }
    return urlStr;
  }

  /// 获取uri除query的短链
  static String getUrlKey(String urlStr) {
    Uri uri = Uri.parse(autoCompleteUrl(urlStr));
    return uri.scheme + "://" + uri.host + uri.path;
  }

  /// 检测uri合法性
  static bool checkUri(Uri uri) {
    return null != uri &&
        uri.scheme.isNotEmpty &&
        uri.host.isNotEmpty &&
        uri.path.isNotEmpty;
  }

  /// 合并uri query 和入参的params
  static Map<String, dynamic> mergeParams(String query,
      {Map<String, dynamic> extraParams}) {
    Map<String, dynamic> allParams = Map();
    Map<String, String> urlParams = Uri.splitQueryString(query) ?? {};
    allParams.addAll(urlParams);
    if (null != extraParams && extraParams.isNotEmpty) {
      allParams.addAll(extraParams);
    }
    return allParams;
  }
}
