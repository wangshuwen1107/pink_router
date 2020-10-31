package cn.cheney.flutter.pink.pink_router_example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.cheney.flutter.pink.pink_router.ProtocolCallback
import cn.cheney.flutter.pink.pink_router.PinkRouter
import cn.cheney.flutter.pink.pink_router.ResultCallback
import io.flutter.app.FlutterApplication
import java.io.Serializable
import java.lang.annotation.Native

class App : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        initRouter()
    }

    companion object {
        val TAG = App::javaClass.name
    }

    private fun initRouter() {
        PinkRouter.setProtocolCallback(object : ProtocolCallback {

            override fun openContainer(context: Context, url: String,
                                       params: Map<String, Any>?, callback: ResultCallback) {
                Log.i(TAG, "onPush  url=$url params=$params")
                when (url) {
                    "pink://test/nativePageA" -> {
                        NativeAActivity.start(context, params as Serializable, callback)
                    }
                    "pink://test/nativePageB" -> {
                        NativeBActivity.start(context, params as Serializable)
                    }
                }
            }

            override fun invokeMethod(context: Context, url: String,
                                      params: Map<String, Any>?, callback: ResultCallback) {
                Log.i(TAG, "onPush  url=$url params=$params")
            }

        })
    }

}


