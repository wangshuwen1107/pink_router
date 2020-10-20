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
            "page" -> {
                onPageAction(call.arguments)
            }
            "method" -> {
            }
            "register" -> {
                onRegisterRouter(call.arguments as List<*>)
            }
            else -> {
            }
        }
    }


    fun release() {
        methodChannel.setMethodCallHandler(null)
    }

    private fun onRegisterRouter(routerList: List<*>) {
        val routerStrList = routerList.filterIsInstance<String>()
        Log.i(PinkRouterPlugin.TAG, "onRegisterRouter $routerStrList")
        this.routerList = routerStrList
    }


    private fun onPageAction(arg:Any){

    }


}
