package cn.cheney.flutter.pink.pink_router

import android.content.Context
import io.flutter.plugin.common.MethodChannel

interface NativeCallback {
    fun onPush(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
    fun onCall(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
}

typealias ResultCallback = (Any?) -> Unit

object PinkRouter {

    @JvmStatic
    fun init(context: Context) {
        NativeActivityRecord.registerCallbacks(context)
        PinkRouterWrapper.init(context)
    }

    @JvmStatic
    fun push(url: String, params: Map<String, Any>? = null, callback: ResultCallback? = null) {
        PinkRouterWrapper.push(url, params, callback)
    }

    @JvmStatic
    fun pop(result: Any? = null) {
        PinkRouterWrapper.pop(result)
    }

    @JvmStatic
    fun call(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        PinkRouterWrapper.call(url, params, callback)
    }


    @JvmStatic
    fun setProtocolCallback(callback: NativeCallback) {
        Config.callback = callback
    }


    internal object Config {

        var callback: NativeCallback? = null

        fun onPush(context: Context, url: String,
                   params: Map<String, Any>?, result: MethodChannel.Result) {
            callback?.onPush(context, url, params) {
                result.success(it)
            }
        }

        fun onCall(context: Context, url: String,
                   params: Map<String, Any>?, result: MethodChannel.Result) {
            callback?.onCall(context, url, params) {
                result.success(it)
            }
        }

    }

}




