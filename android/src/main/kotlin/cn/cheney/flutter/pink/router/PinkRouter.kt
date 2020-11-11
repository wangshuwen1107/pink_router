package cn.cheney.flutter.pink.router

import android.content.Context
import cn.cheney.flutter.pink.router.core.NativeActivityRecord
import cn.cheney.flutter.pink.router.core.PinkRouterImpl
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
        PinkRouterImpl.init(context)
    }

    @JvmStatic
    fun push(url: String, params: Map<String, Any>? = null, callback: ResultCallback? = null) {
        PinkRouterImpl.push(url, params, callback)
    }

    @JvmStatic
    fun pop(result: Any? = null) {
        PinkRouterImpl.pop(result)
    }

    @JvmStatic
    fun call(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        PinkRouterImpl.call(url, params, callback)
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




