package cn.cheney.flutter.pink.pink_router.channel

import android.text.TextUtils
import cn.cheney.flutter.pink.pink_router.Logger
import cn.cheney.flutter.pink.pink_router.NativeActivityRecord
import cn.cheney.flutter.pink.pink_router.PinkRouter

class ReceiverChannel(private val channel: ChannelProxy) {

    private var routerList: List<String> = listOf()

    init {
        onRegisterRouter()
        onPush()
        onCall()
    }

    private fun onRegisterRouter() {
        channel.registerMethod("registerRouter") { args, result ->
            val routerStrList = (args as? List<*>?)?.filterIsInstance<String>()
            this.routerList = routerStrList ?: arrayListOf()
            Logger.d("Native received  $routerStrList")
            result.success("success")
        }
    }


    private fun onPush() {
        channel.registerMethod("push") { args, result ->
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
            PinkRouter.Config.onPush(topActivity, url!!, paramsMap, result)
        }
    }


    private fun onCall() {
        channel.registerMethod("call") { args, result ->
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
            PinkRouter.Config.onPush(topActivity, url!!, paramsMap, result)
        }
    }


}