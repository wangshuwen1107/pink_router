package cn.cheney.flutter.pink.pink_router

import android.text.TextUtils
import android.util.Log
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class PinkRouterChannel(messenger: BinaryMessenger) : MethodChannel.MethodCallHandler {

    private val methodChannel = MethodChannel(messenger, CHANNEL_NAME)

    private var routerList: List<String> = listOf()

    companion object {
        const val CHANNEL_NAME = "pink_router_channel"
    }

    init {
        methodChannel.setMethodCallHandler(this)
    }


    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        val methodName = call.method
        if (TextUtils.isEmpty(methodName)) {
            result.notImplemented()
            return
        }
        when (methodName) {
            "push" -> {
                onPush(call, result)
            }
            "method" -> {
                onInvokeMethod(call, result)
            }
            "register" -> {
                onRegisterRouter(call.arguments as List<*>)
            }
            else -> {
            }
        }
    }


    private fun onRegisterRouter(routerList: List<*>) {
        val routerStrList = routerList.filterIsInstance<String>()
        Log.i(PinkRouterPlugin.TAG, "onRegisterRouter $routerStrList")
        this.routerList = routerStrList
    }


    private fun onPush(methodCall: MethodCall, result: MethodChannel.Result) {
        val url = methodCall.argument<String?>("url")
        val paramsMap = methodCall.argument<Map<String, Any>?>("params")
        if (TextUtils.isEmpty(url)) {
            result.notImplemented()
            return
        }
        val topActivity = PinkActivityHelper.getTopActivity()
        if (null == topActivity) {
            result.error("NATIVE_TOP_CONTEXT_NULL",
                    "NATIVE_TOP_CONTEXT_NULL_PLEASE_INIT", null)
            return
        }
        PinkRouter.onPush(topActivity, url!!, paramsMap,result)
    }


    private fun onInvokeMethod(methodCall: MethodCall, result: MethodChannel.Result) {
        val url = methodCall.argument<String?>("url")
        val paramsMap = methodCall.argument<Map<String, Any>?>("params")
        if (TextUtils.isEmpty(url)) {
            result.notImplemented()
            return
        }
        val topActivity = PinkActivityHelper.getTopActivity()
        if (null == topActivity) {
            result.error("NATIVE_TOP_CONTEXT_NULL",
                    "NATIVE_TOP_CONTEXT_NULL_PLEASE_INIT", null)
            return
        }
        PinkRouter.onMethodInvoke(topActivity, url!!, paramsMap,result)
    }


    fun release() {
        methodChannel.setMethodCallHandler(null)
    }


}
