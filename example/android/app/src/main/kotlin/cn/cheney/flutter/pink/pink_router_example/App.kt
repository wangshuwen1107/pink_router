package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.util.Log
import cn.cheney.flutter.pink.pink_router.NativeCallback
import cn.cheney.flutter.pink.pink_router.PinkRouter
import cn.cheney.flutter.pink.pink_router.ResultCallback
import io.flutter.app.FlutterApplication
import java.io.Serializable

class App : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        initRouter()
    }

    companion object {
        val TAG = App::javaClass.name
    }

    private fun initRouter() {
        PinkRouter.init(context = this)
        PinkRouter.setProtocolCallback(object : NativeCallback {
            override fun onPush(context: Context,
                                url: String,
                                params: Map<String, Any>?,
                                result: ResultCallback) {
                Log.i(TAG, "protocolStart  url=$url params=$params")
                when (url) {
                    "pink://test/nativePageA" -> {
                        NativeAActivity.start(context, params as? Serializable?, result)
                    }
                    "pink://test/nativePageB" -> {
                        NativeBActivity.start(context, params as? Serializable?)
                    }
                }
            }

            override fun onCall(context: Context,
                                url: String, params: Map<String, Any>?,
                                result: ResultCallback) {
            }

        })
    }

}


