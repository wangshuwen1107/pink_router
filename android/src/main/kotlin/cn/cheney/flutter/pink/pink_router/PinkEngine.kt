package cn.cheney.flutter.pink.pink_router

import android.content.Context
import cn.cheney.flutter.pink.pink_router.channel.ChannelProxy
import cn.cheney.flutter.pink.pink_router.channel.ReceiverChannel
import cn.cheney.flutter.pink.pink_router.channel.SendChannel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.view.FlutterMain

class PinkEngine(context: Context) {

    var sendChannel: SendChannel

    private var receiverChannel: ReceiverChannel

    private var engine: FlutterEngine = FlutterEngine(context)


    init {
        val channelProxy = ChannelProxy("main", engine.dartExecutor)
        sendChannel = SendChannel(channelProxy)
        receiverChannel = ReceiverChannel(channelProxy)

        val dartEntryPoint = DartExecutor.DartEntrypoint(FlutterMain.findAppBundlePath(), "main")

        engine.dartExecutor.executeDartEntrypoint(dartEntryPoint)
        FlutterEngineCache.getInstance().put("main", engine)

    }



}