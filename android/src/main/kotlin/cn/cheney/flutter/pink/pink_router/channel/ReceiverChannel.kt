package cn.cheney.flutter.pink.pink_router.channel

import android.text.TextUtils
import android.util.Log
import cn.cheney.flutter.pink.pink_router.Logger
import cn.cheney.flutter.pink.pink_router.NativeActivityRecord
import cn.cheney.flutter.pink.pink_router.PinkRouter
import cn.cheney.flutter.pink.pink_router.PinkRouterPlugin

class ReceiverChannel(private val channel: ChannelProxy) {

    private var routerList: List<String> = listOf()

    init {
        onRegisterRouter()
        onProtocolStart()
    }

    private fun onRegisterRouter() {
        channel.registerMethod("registerRouter") { args, result ->
            val routerStrList = (args as? List<*>?)?.filterIsInstance<String>()
            this.routerList = routerStrList ?: arrayListOf()
            Logger.d("Native received  $routerStrList")
            result.success("success")
        }
    }


    private fun onProtocolStart() {
        channel.registerMethod("navigation") { args, result ->
            val argMap = (args as? Map<*, *>) ?: mapOf<String, Any>()
            val url = argMap["url"] as? String?
            val paramsMap = argMap["params"] as? Map<String, Any>?
            if (TextUtils.isEmpty(url)) {
                result.error("URL_EMPTY", "URL_EMPTY", null)
                return@registerMethod
            }
            val topActivity = NativeActivityRecord.getTopActivity()
            if (null == topActivity) {
                result.error("NATIVE_TOP_CONTEXT_NULL",
                        "NATIVE_TOP_CONTEXT_NULL_PLEASE_INIT", null)
                return@registerMethod
            }
            PinkRouter.Config.onNavigation(topActivity, url!!, paramsMap, result)
        }
    }


}