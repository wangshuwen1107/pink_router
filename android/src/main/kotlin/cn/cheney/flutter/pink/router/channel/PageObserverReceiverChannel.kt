package cn.cheney.flutter.pink.router.channel

import android.text.TextUtils
import cn.cheney.flutter.pink.router.core.observer.PageObserverManager
import cn.cheney.flutter.pink.router.entity.RouteSettings

class PageObserverReceiverChannel(private val channel: ChannelProxy) {

    init {
        onCreate()
        onWillAppear()
        onDidAppear()
        onWillDisappear()
        onDidDisappear()
        onDestroy()
    }

    private fun onCreate() {
        channel.registerMethod("create") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.create(routeSettings)
                result.success(true)
            }
        }
    }

    private fun onWillAppear() {
        channel.registerMethod("willAppear") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.willAppear(routeSettings)
                result.success(true)
            }
        }
    }

    private fun onDidAppear() {
        channel.registerMethod("didAppear") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.didAppear(routeSettings)
                result.success(true)
            }
        }
    }

    private fun onWillDisappear() {
        channel.registerMethod("willDisappear") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.willDisappear(routeSettings)
                result.success(true)
            }
        }
    }

    private fun onDidDisappear() {
        channel.registerMethod("didDisappear") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.didDisappear(routeSettings)
                result.success(true)
            }
        }
    }

    private fun onDestroy() {
        channel.registerMethod("destroy") { args, result ->
            val routeSettings = toRouteSettings(args)
            if (null == routeSettings) {
                result.success(false)
            } else {
                PageObserverManager.destroy(routeSettings)
                result.success(true)
            }
        }
    }


    private fun toRouteSettings(args: Any?): RouteSettings? {
        val argsMap = args as? Map<*, *>
        argsMap?.let {
            val url = it["url"] as? String
            val params = it["params"] as? Map<String, Any>
            if (TextUtils.isEmpty(url)) {
                return null
            }
            return RouteSettings.flutter(url!!, params)
        }
        return null
    }


}