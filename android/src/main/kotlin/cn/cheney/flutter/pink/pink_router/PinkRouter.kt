package cn.cheney.flutter.pink.pink_router

import android.content.Context
import cn.cheney.flutter.pink.pink_router.util.Logger
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.PinkFlutterActivity
import io.flutter.embedding.android.containerId
import io.flutter.embedding.android.index
import io.flutter.plugin.common.MethodChannel
import java.util.*

interface NativeCallback {
    fun onPush(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
    fun onCall(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
}

typealias ResultCallback = (Any?) -> Unit

class PinkRouter {

    companion object {

        fun push(url: String, params: Map<String, Any>? = null, callback: ResultCallback? = null) {
            PinkRouterWrapper.push(url, params, callback)
        }


        fun pop(params: Any? = null) {
            PinkRouterWrapper.pop(params)
        }

        fun call(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
            PinkRouterWrapper.call(url, params, callback)
        }

        fun init(context: Context) {
            NativeActivityRecord.registerCallbacks(context)
            PinkRouterWrapper.init(context)
        }

        fun setProtocolCallback(callback: NativeCallback) {
            Config.callback = callback
        }

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




