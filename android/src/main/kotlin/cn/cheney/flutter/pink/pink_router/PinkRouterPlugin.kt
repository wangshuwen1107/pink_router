package cn.cheney.flutter.pink.pink_router

import androidx.annotation.NonNull;
import cn.cheney.flutter.pink.pink_router.util.Logger

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.PluginRegistry.Registrar

/** PinkRouterPlugin */
public class PinkRouterPlugin : FlutterPlugin {

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        Logger.d("onAttachedToEngine init is called ${flutterPluginBinding.binaryMessenger}")
        //channel = ChannelProxy(flutterPluginBinding.binaryMessenger)
        //NativeActivityRecord.registerCallbacks(flutterPluginBinding.applicationContext)
    }

    companion object {


        //lateinit var channel: ChannelProxy

        @JvmStatic
        fun registerWith(registrar: Registrar) {
            //channel = ChannelProxy(registrar.messenger())
            //NativeActivityRecord.registerCallbacks(registrar.context())
        }

    }


    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        //channel.release()
    }


}
