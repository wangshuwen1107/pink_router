package cn.cheney.flutter.pink.router.core.observer

import cn.cheney.flutter.pink.router.core.ActivityDelegate
import cn.cheney.flutter.pink.router.core.PinkRouterImpl
import cn.cheney.flutter.pink.router.entity.RouteSettings
import cn.cheney.flutter.pink.router.util.Logger
import io.flutter.embedding.android.PinkActivity
import io.flutter.embedding.android.params
import io.flutter.embedding.android.url

internal object PageObserverManager {

    var pageObserverList: MutableList<PageObserver> = mutableListOf()

    fun addPageLifeCycleObserver(observer: PageObserver) {
        pageObserverList.add(observer)
    }


    fun create(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.create(routeSettings)
        }
        pageObserverList.forEach {
            it.create(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings create")
    }

    fun willAppear(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.willAppear(routeSettings)
        } else {
            val previousActivity = ActivityDelegate.getPreviousActivity()
            if (previousActivity is PinkActivity) {
                willDisappear(RouteSettings.flutter(previousActivity.url(),
                        previousActivity.params() ?: mapOf<String, Any>()))
            }
        }
        pageObserverList.forEach {
            it.willAppear(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings willAppear")
    }

    fun didAppear(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.didAppear(routeSettings)
        } else {
            val previousActivity = ActivityDelegate.getPreviousActivity()
            if (previousActivity is PinkActivity) {
                didDisappear(RouteSettings.flutter(previousActivity.url(),
                        previousActivity.params() ?: mapOf<String, Any>()))
            }
        }
        pageObserverList.forEach {
            it.didAppear(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings didAppear")
    }

    fun willDisappear(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.willDisappear(routeSettings)
        } else {
            val previousActivity = ActivityDelegate.getPreviousActivity()
            if (previousActivity is PinkActivity) {
                willAppear(RouteSettings.flutter(previousActivity.url(),
                        previousActivity.params() ?: mapOf<String, Any>()))
            }
        }
        pageObserverList.forEach {
            it.willDisappear(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings willDisappear")
    }

    fun didDisappear(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.didDisappear(routeSettings)
        } else {
            val previousActivity = ActivityDelegate.getPreviousActivity()
            if (previousActivity is PinkActivity) {
                didAppear(RouteSettings.flutter(previousActivity.url(),
                        previousActivity.params() ?: mapOf<String, Any>()))
            }
        }
        pageObserverList.forEach {
            it.didDisappear(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings didDisappear")
    }

    fun destroy(routeSettings: RouteSettings) {
        if (routeSettings.isFlutter) {
            PinkRouterImpl.engine.pageObserverSendChannel.destroy(routeSettings)
        }
        pageObserverList.forEach {
            it.destroy(routeSettings)
        }
        Logger.d("LifeCycle $routeSettings destroy")
    }


}