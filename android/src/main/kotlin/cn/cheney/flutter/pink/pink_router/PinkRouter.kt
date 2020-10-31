package cn.cheney.flutter.pink.pink_router

import android.content.Context
import io.flutter.plugin.common.MethodChannel

interface ProtocolCallback {
    fun openContainer(context: Context, url: String, params: Map<String, Any>?, callback: ResultCallback)
    fun invokeMethod(context: Context, url: String, params: Map<String, Any>?, callback: ResultCallback)
}

typealias ResultCallback = (Any) -> Unit

object PinkRouter {

    private var callback: ProtocolCallback? = null


    fun setProtocolCallback(callback: ProtocolCallback) {
        this.callback = callback
    }

    fun onPush(context: Context, url: String,
               params: Map<String, Any>?, result: MethodChannel.Result) {
        callback?.openContainer(context, url, params) {
            result.success(it)
        }
    }

    fun onMethodInvoke(context: Context, url: String,
                       params: Map<String, Any>?, result: MethodChannel.Result) {
        callback?.invokeMethod(context, url, params) {
            result.success(it)
        }
    }

    fun push(url: String, params: Map<String, Any>?) {
        
    }

}
