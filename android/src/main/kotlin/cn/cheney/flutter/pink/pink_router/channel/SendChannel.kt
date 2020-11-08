package cn.cheney.flutter.pink.pink_router.channel

import cn.cheney.flutter.pink.pink_router.ResultCallback
import cn.cheney.flutter.pink.pink_router.util.Logger
import io.flutter.plugin.common.MethodChannel

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


    fun pop(result: Any?, callback: ResultCallback? = null) {
        Logger.d("本地 入参=$result --->")
        channel.invokeMethod("pop", result, object : MethodChannel.Result {

            override fun notImplemented() {
                callback?.invoke(false)
            }

            override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                Logger.d("本地 入参=$result 结果$result errorCode=$errorCode  errorMessage=$errorMessage")
                callback?.invoke(false)
            }

            override fun success(flutterResult: Any?) {
                Logger.d("本地 入参=$result 结果=$flutterResult")
                callback?.invoke(flutterResult)
            }

        })
    }

    fun sendEvent(name: String, params: Map<String, Any?>?) {

    }
}
