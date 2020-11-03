package cn.cheney.flutter.pink.pink_router

import android.content.Context
import android.content.Intent
import io.flutter.embedding.android.PinkFlutterActivity
import io.flutter.plugin.common.MethodChannel

interface ProtocolCallback {
    fun onPush(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
    fun onCall(context: Context, url: String, params: Map<String, Any>?, result: ResultCallback)
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

    /**
     * 打开flutter页面
     */
    fun push(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        val topActivity = NativeActivityRecord.getTopActivity()
        if (null == topActivity) {
            callback?.invoke("error_context")
            return
        }
        val intent = Intent(topActivity, PinkFlutterActivity::class.java)
        intent.putExtra(PinkFlutterActivity.KEY_ENGINE_ID, "main")
        intent.putExtra(PinkFlutterActivity.KEY_URL, url)
        params?.let {
            intent.putExtra(PinkFlutterActivity.KEY_PARAMS, it as HashMap)
        }
        topActivity.startActivity(intent)
    }

    /**
     * 执行flutter方法
     */
    fun call(url: String, params: Map<String, Any>?, callback: ResultCallback?){
        engine.sendChannel.call(url, params, callback)
    }


    internal object Config {

        var callback: ProtocolCallback? = null

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




