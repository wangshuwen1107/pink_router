package cn.cheney.flutter.pink.pink_router_example

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import cn.cheney.flutter.pink.pink_router.RouterCallback
import cn.cheney.flutter.pink.pink_router.PinkRouter
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
        PinkRouter.setNativeCallback(object : RouterCallback {

            override fun onPush(context: Context, requestCode: Int, url: String, params: Map<String, Any>?) {
                Log.i(TAG, "openActivity  context=$context url=$url params=$params")
                when (url) {
                    "pink://test/nativePageA" -> {
                        val intent = Intent(context, Native1Activity::class.java)
                        intent.putExtra("params", params as Serializable)
                        val activity = (context as Activity)
                        activity.startActivityForResult(intent, requestCode)
                    }
                }
            }

            override fun invokeMethod(context: Context, url: String, params: Map<String, Any>?) {
            }
        })
    }

}


