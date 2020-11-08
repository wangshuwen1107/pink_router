package cn.cheney.flutter.pink.pink_router.channel

import cn.cheney.flutter.pink.pink_router.ResultCallback
import cn.cheney.flutter.pink.pink_router.util.Logger
import io.flutter.plugin.common.MethodChannel
import java.util.*

class SendChannel(private val channel: ChannelProxy) {

    fun push(url: String, params: Map<String, Any?>?, callback: ResultCallback?) {
        val arguments = mutableMapOf<String, Any>().also {
            it["url"] = url
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        channel.invokeMethod("push", arguments, object : MethodChannel.Result {
            override fun notImplemented() {
                callback?.invoke(mutableMapOf<String, String>().also {
                    it["errorCode"] = "notImplemented"
                    it["errorMessage"] = "notImplemented"
                })
            }

            override fun error(errorCode: String?,
                               errorMessage: String?,
                               errorDetails: Any?) {
                callback?.invoke(mutableMapOf<String, String?>().also {
                    it["errorCode"] = errorCode
                    it["errorMessage"] = errorMessage
                })
            }

            override fun success(result: Any?) {
                callback?.invoke(result)
            }
        })
    }


    fun call(url: String, params: Map<String, Any?>?, callback: ResultCallback?) {
        val arguments = mutableMapOf<String, Any>().also {
            it["url"] = url
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        channel.invokeMethod("call", arguments, object : MethodChannel.Result {
            override fun notImplemented() {
                callback?.invoke(mutableMapOf<String, String>().also {
                    it["errorCode"] = "notImplemented"
                    it["errorMessage"] = "notImplemented"
                })
            }

            override fun error(errorCode: String?,
                               errorMessage: String?,
                               errorDetails: Any?) {
                callback?.invoke(mutableMapOf<String, String?>().also {
                    it["errorCode"] = errorCode
                    it["errorMessage"] = errorMessage
                })
            }

            override fun success(result: Any?) {
                callback?.invoke(result)
            }
        })
    }


    fun pop(result: Any?, isBackPress: Boolean = false, callback: ResultCallback? = null) {
        val args = mutableMapOf<String, Any>()
        result?.let {
            args["result"] = it
        }
        args["isBackPress"] = isBackPress
        channel.invokeMethod("pop", args, object : MethodChannel.Result {

            override fun notImplemented() {
                callback?.invoke(false)
            }

            override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                callback?.invoke(false)
            }

            override fun success(flutterResult: Any?) {
                callback?.invoke(flutterResult)
            }

        })
    }

    fun sendEvent(name: String, params: Map<String, Any?>?) {

    }
}
