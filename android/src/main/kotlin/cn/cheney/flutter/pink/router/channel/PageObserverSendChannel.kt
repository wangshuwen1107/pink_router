package cn.cheney.flutter.pink.router.channel

import cn.cheney.flutter.pink.router.entity.RouteSettings
import io.flutter.plugin.common.MethodChannel

class PageObserverSendChannel(private val channel: ChannelProxy) {


    fun create(routeSettings: RouteSettings) {
        sendLifeCycle("create", routeSettings)
    }

    fun willAppear(routeSettings: RouteSettings) {
        sendLifeCycle("willAppear", routeSettings)
    }

    fun didAppear(routeSettings: RouteSettings) {
        sendLifeCycle("didAppear", routeSettings)
    }

    fun willDisappear(routeSettings: RouteSettings) {
        sendLifeCycle("willDisappear", routeSettings)
    }

    fun didDisappear(routeSettings: RouteSettings) {
        sendLifeCycle("didDisappear", routeSettings)
    }

    fun destroy(routeSettings: RouteSettings) {
        sendLifeCycle("destroy", routeSettings)
    }

    private fun sendLifeCycle(lifeCycle: String, routeSettings: RouteSettings) {
        channel.invokeMethod(lifeCycle, mutableMapOf("url" to routeSettings.url,
                "params" to routeSettings.params),
                object : MethodChannel.Result {
                    override fun success(result: Any?) {
                    }

                    override fun error(errorCode: String?, errorMessage: String?, errorDetails: Any?) {
                    }

                    override fun notImplemented() {
                    }

                })
    }
}