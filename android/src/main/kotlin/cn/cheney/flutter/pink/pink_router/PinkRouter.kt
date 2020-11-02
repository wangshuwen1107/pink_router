package cn.cheney.flutter.pink.pink_router

import android.content.Context
import android.util.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel

interface ProtocolCallback {
    fun onNavigation(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
}

typealias ResultCallback = (Any?) -> Unit

object PinkRouter {

    lateinit var engine: PinkEngine

    fun init(context: Context) {
        Logger.d("init is called ")
        engine = PinkEngine(context)
        NativeActivityRecord.registerCallbacks(context)
    }

    fun setProtocolCallback(callback: ProtocolCallback) {
        Config.callback = callback
    }

    fun navigation(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        val topActivity = NativeActivityRecord.getTopActivity()
        if (null == topActivity) {
            callback?.invoke("error_context")
            return
        }
        val intent = FlutterActivity.withCachedEngine("main")
                .build(topActivity)
                .putExtra("url", url)
        params?.let {
            intent.putExtra("url", it as HashMap)
        }
        topActivity.startActivity(intent)

    }

    internal object Config {

        var callback: ProtocolCallback? = null

        fun onNavigation(context: Context, url: String,
                         params: Map<String, Any>?, result: MethodChannel.Result) {
            callback?.onNavigation(context, url, params) {
                result.success(it)
            }
        }

    }


}




