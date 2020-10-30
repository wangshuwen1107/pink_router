package cn.cheney.flutter.pink.pink_router

import android.content.Context
import androidx.annotation.UiThread
import io.flutter.plugin.common.MethodChannel
import java.nio.channels.Channel

interface RouterCallback {
    fun onPush(context: Context, requestCode: Int, url: String, params: Map<String, Any>?)
    fun invokeMethod(context: Context, url: String, params: Map<String, Any>?)
}


object PinkRouter {

    private var callback: RouterCallback? = null


    fun setNativeCallback(callback: RouterCallback) {
        this.callback = callback
    }

    fun onPush(context: Context, requestCode: Int, url: String, params: Map<String, Any>?) {
        callback?.onPush(context, requestCode, url, params)
    }

    fun onMethodInvoke(context: Context, url: String, params: Map<String, Any>?) {
        callback?.invokeMethod(context, url, params)
    }


    fun push(url: String, params: Map<String, Any>?) {

    }


}
