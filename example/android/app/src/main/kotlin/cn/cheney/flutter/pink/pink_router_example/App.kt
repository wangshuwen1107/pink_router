package cn.cheney.flutter.pink.pink_router_example

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import cn.cheney.flutter.pink.pink_router.NativeRouterCallback
import cn.cheney.flutter.pink.pink_router.PinkRouter
import io.flutter.app.FlutterApplication
import java.io.Serializable
import java.net.URI
import java.net.URL

class App : FlutterApplication() {

    override fun onCreate() {
        super.onCreate()
        initRouter()
    }

    companion object {
        val TAG = App::javaClass.name
    }

    private fun initRouter() {
        PinkRouter.setNativeCallback(object : NativeRouterCallback {
            override fun openActivity(context: Context, url: String, params: Map<String, Any>?) {
                Log.i(TAG, "openActivity  context=$context url=$url params=$params")
                val urlObject = Uri.parse(url)
                when (url) {
                    "pink://test/nativePageA" -> {
                        val intent = Intent(context, NativeAActivity::class.java)
                        intent.putExtra("params", params as Serializable)
                        context.startActivity(intent)
                    }
                }
            }

            override fun invokeMethod(context: Context, url: String, params: Map<String, Any?>?) {
            }
        })
    }

}