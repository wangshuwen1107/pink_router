package cn.cheney.flutter.pink.router.channel

import cn.cheney.flutter.pink.router.ResultCallback
import cn.cheney.flutter.pink.router.util.Logger
import io.flutter.plugin.common.MethodChannel

class SendChannel(private val channel: ChannelProxy) {


    class ResultCallbackProxy(private val callback: ResultCallback?) : MethodChannel.Result {


        override fun notImplemented() {
            callback?.invoke(false)
            Logger.e("notImplemented")
        }

        override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
            callback?.invoke(false)
            Logger.e("errorCode=$errorCode errorMessage=$errorMessage errorDetails=$errorDetails")
        }

        override fun success(result: Any?) {
            callback?.invoke(result)
        }


    }

    fun push(url: String, params: Map<String, Any?>?, callback: ResultCallback? = null) {
        val arguments = mutableMapOf<String, Any>().also {
            it["url"] = url
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        channel.invokeMethod("push", arguments, ResultCallbackProxy(callback))
    }


    fun call(url: String, params: Map<String, Any?>?, callback: ResultCallback?) {
        val arguments = mutableMapOf<String, Any>().also {
            it["url"] = url
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        channel.invokeMethod("call", arguments, ResultCallbackProxy(callback))
    }


    fun pop(result: Any?, isBackPress: Boolean = false, callback: ResultCallback? = null) {
        val args = mutableMapOf<String, Any>()
        result?.let {
            args["result"] = it
        }
        args["isBackPress"] = isBackPress
        channel.invokeMethod("pop", args, ResultCallbackProxy(callback))
    }


    fun sendEvent(name: String, params: Map<String, Any?>?) {

    }
}
