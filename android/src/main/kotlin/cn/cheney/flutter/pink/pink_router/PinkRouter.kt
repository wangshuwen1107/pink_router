package cn.cheney.flutter.pink.pink_router

import android.content.Context

interface NativeRouterCallback {
    fun openActivity(context: Context, url: String, params: Map<String, Any>?)
    fun invokeMethod(context: Context, url: String, params: Map<String, Any?>?)
}

object PinkRouter {

    private var callback: NativeRouterCallback? =null

    fun setNativeCallback(callback:NativeRouterCallback){
        this.callback = callback
    }

    fun callbackOpenActivity(context: Context, url: String, params: Map<String, Any>?){
        callback?.openActivity(context, url, params)
    }

}
