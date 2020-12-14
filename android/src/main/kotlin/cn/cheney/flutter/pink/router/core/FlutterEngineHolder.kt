package cn.cheney.flutter.pink.router.core

import android.content.Context
import cn.cheney.flutter.pink.router.channel.*
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain

class FlutterEngineHolder(context: Context) {

    private val routeReceiverChannel: RouteReceiverChannel
    private val pageObserverReceiverChannel: PageObserverReceiverChannel
    
    val routeSendChannel: RouteSendChannel
    val pageObserverSendChannel:PageObserverSendChannel
  

    init {
        val flutterEngine = FlutterEngine(context)
        
        val channelProxy = ChannelProxy(ENGINE_ID, flutterEngine.dartExecutor)
        routeSendChannel = RouteSendChannel(channelProxy)
        routeReceiverChannel = RouteReceiverChannel(channelProxy)
        pageObserverReceiverChannel = PageObserverReceiverChannel(channelProxy)
        pageObserverSendChannel = PageObserverSendChannel(channelProxy)
        
        
        val dartEntryPoint = DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(),
                ENGINE_ID)
        
        flutterEngine.dartExecutor.executeDartEntrypoint(dartEntryPoint)
        
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
    }


    companion object {
        const val ENGINE_ID = "main"
    }

}