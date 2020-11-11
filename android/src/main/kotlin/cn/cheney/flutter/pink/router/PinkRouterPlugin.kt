package cn.cheney.flutter.pink.router

import androidx.annotation.NonNull
import cn.cheney.flutter.pink.router.util.Logger

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.PluginRegistry.Registrar

/** PinkRouterPlugin */
public class PinkRouterPlugin : FlutterPlugin {

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        Logger.d("onAttachedToEngine init is called ${flutterPluginBinding.binaryMessenger}")
    }

    companion object {

        @JvmStatic
        fun registerWith(registrar: Registrar) {
        }

    }


    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        //channel.release()
    }


}
