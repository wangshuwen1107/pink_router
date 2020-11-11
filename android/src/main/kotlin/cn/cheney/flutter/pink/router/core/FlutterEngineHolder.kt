package cn.cheney.flutter.pink.router.core

import android.content.Context
import cn.cheney.flutter.pink.router.channel.ChannelProxy
import cn.cheney.flutter.pink.router.channel.ReceiverChannel
import cn.cheney.flutter.pink.router.channel.SendChannel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain

class FlutterEngineHolder(context: Context) {

    var sendChannel: SendChannel

    private var receiverChannel: ReceiverChannel


    init {
        val flutterEngine = FlutterEngine(context)
        
        val channelProxy = ChannelProxy(ENGINE_ID, flutterEngine.dartExecutor)
        sendChannel = SendChannel(channelProxy)
        receiverChannel = ReceiverChannel(channelProxy)
        val dartEntryPoint = DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(),
                ENGINE_ID)
        
        flutterEngine.dartExecutor.executeDartEntrypoint(dartEntryPoint)
        
        FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
    }


    companion object {
        const val ENGINE_ID = "main"
    }

}