package cn.cheney.flutter.pink.router.core.observer

import cn.cheney.flutter.pink.router.model.RouteSettings


interface PageObserver {

    fun create(routeSettings: RouteSettings) {}

    fun willAppear(routeSettings: RouteSettings) {}

    fun didAppear(routeSettings: RouteSettings) {}

    fun willDisappear(routeSettings: RouteSettings) {}

    fun didDisappear(routeSettings: RouteSettings) {}

    fun destroy(routeSettings: RouteSettings) {}
    
}