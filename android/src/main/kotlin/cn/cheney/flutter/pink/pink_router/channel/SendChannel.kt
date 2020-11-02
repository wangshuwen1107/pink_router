package cn.cheney.flutter.pink.pink_router.channel

import cn.cheney.flutter.pink.pink_router.ResultCallback
import io.flutter.plugin.common.MethodChannel

class SendChannel(private val channel: ChannelProxy) {

    fun navigation(url: String, params: Map<String, Any?>?, callback: ResultCallback?) {
        channel.invokeMethod(url, params, object : MethodChannel.Result {
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


    fun sendEvent(name: String, params: Map<String, Any?>?) {

    }
}
