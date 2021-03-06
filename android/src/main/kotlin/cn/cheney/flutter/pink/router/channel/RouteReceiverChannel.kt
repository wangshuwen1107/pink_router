package cn.cheney.flutter.pink.router.channel

import android.text.TextUtils
import cn.cheney.flutter.pink.router.core.ActivityDelegate
import cn.cheney.flutter.pink.router.PinkRouter
import cn.cheney.flutter.pink.router.core.PinkRouterImpl

class RouteReceiverChannel(private val channel: ChannelProxy) {

    private var routerList: List<String> = listOf()

    init {
        onRegisterRouter()
        onPush()
        onPop()
        onCall()
    }

    private fun onRegisterRouter() {
        channel.registerMethod("registerRouter") { args, result ->
            val routerStrList = (args as? List<*>?)?.filterIsInstance<String>()
            this.routerList = routerStrList ?: arrayListOf()
            result.success(true)
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
            val topActivity = ActivityDelegate.getTopActivity()
            if (null == topActivity) {
                result.error("NATIVE_TOP_CONTEXT_NULL",
                        "NATIVE_TOP_CONTEXT_NULL_PLEASE_INIT", null)
                return@registerMethod
            }
            if (routerList.contains(url)) {
                PinkRouterImpl.push(url!!, paramsMap) {
                    result.success(it)
                }
            } else {
                PinkRouter.Config.onPush(topActivity, url!!, paramsMap, result)
            }
        }
    }


    private fun onPop() {
        channel.registerMethod("pop") { args, result ->
            PinkRouterImpl.pop(args)
            result.success(true)
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
            val topActivity = ActivityDelegate.getTopActivity()
            if (null == topActivity) {
                result.error("NATIVE_TOP_CONTEXT_NULL",
                        "NATIVE_TOP_CONTEXT_NULL_PLEASE_INIT", null)
                return@registerMethod
            }
            PinkRouter.Config.onCall(topActivity, url!!, paramsMap, result)
        }
    }


}