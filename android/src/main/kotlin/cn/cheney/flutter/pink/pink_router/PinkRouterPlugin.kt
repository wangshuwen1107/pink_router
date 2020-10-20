package cn.cheney.flutter.pink.pink_router

import android.text.TextUtils
import android.util.Log
import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** PinkRouterPlugin */
public class PinkRouterPlugin : FlutterPlugin {


    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        Log.i(TAG, "onAttachedToEngine  is called ")
        channel = PinkRouterChannel(flutterPluginBinding.binaryMessenger)
    }

    companion object {

        const val TAG = "PinkRouterPlugin"

        lateinit var channel: PinkRouterChannel

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            Log.i(TAG, "registerWith is called ")
            channel = PinkRouterChannel(registrar.messenger())
        }
    }


    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.release()
    }


}
