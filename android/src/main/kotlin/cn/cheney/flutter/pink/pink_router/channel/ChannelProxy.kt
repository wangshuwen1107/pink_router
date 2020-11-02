package cn.cheney.flutter.pink.pink_router.channel

import android.text.TextUtils
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel


typealias MethodHandler = (Any?, MethodChannel.Result) -> Unit
typealias EventHandler = (String,Any?) -> Unit

class ChannelProxy(private val name: String, messenger: BinaryMessenger) {

    private val methodChannel = MethodChannel(messenger, name + "_method_channel")
    private val eventChannel = EventChannel(messenger, name + "_event_channel")

    private val methodHandleMap = mutableMapOf<String, MethodHandler>()
    private val eventHandlerMap = mutableMapOf<String, EventHandler>()

    private var eventSink: EventChannel.EventSink? = null


    init {
        methodChannel.setMethodCallHandler(object : MethodChannel.MethodCallHandler {
            override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
                val methodName = call.method
                if (TextUtils.isEmpty(methodName)) {
                    result.notImplemented()
                    return
                }
                val methodHandler = methodHandleMap[methodName]
                if (null == methodHandler) {
                    result.notImplemented()
                    return
                }
                methodHandler.invoke(call.arguments, result)
            }
        })
        eventChannel.setStreamHandler(object : EventChannel.StreamHandler {
            override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
                this@ChannelProxy.eventSink = events
                TODO("event callback")
            }

            override fun onCancel(arguments: Any?) {
            }
        })

    }


    internal fun registerMethod(name: String, handler: MethodHandler) {
        methodHandleMap[name] = handler
    }


    internal fun registerEvent(name: String, handler: EventHandler) {
        eventHandlerMap[name] = handler
    }


    internal fun sendEvent(name: String, params: Map<String, Any>?) {
        val event = mutableMapOf<String, Any>().also {
            it["name"] = name
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        eventSink?.success(event)
    }

    internal fun invokeMethod(url: String, params: Map<String, Any?>?, callback: MethodChannel.Result) {
        val arguments = mutableMapOf<String, Any>().also {
            it["url"] = url
            it["params"] = params ?: mutableMapOf<String, Any>()
        }
        methodChannel.invokeMethod("navigation", arguments, callback)
    }

}
