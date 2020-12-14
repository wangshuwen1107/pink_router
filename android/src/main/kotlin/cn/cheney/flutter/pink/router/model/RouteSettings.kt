package cn.cheney.flutter.pink.router.model

import android.app.Activity
import java.util.*

class RouteSettings {

    var url: String? = null

    var params: Map<String, Any?>? = null

    var isFlutter: Boolean = false

    var activity: Activity? = null


    companion object {

        fun flutter(url: String, params: Map<String, Any?>): RouteSettings {
            val routerSettings = RouteSettings()
            routerSettings.url = url
            routerSettings.params = params
            routerSettings.isFlutter = true
            return routerSettings
        }

        fun native(activity: Activity): RouteSettings {
            val routerSettings = RouteSettings()
            routerSettings.activity = activity
            routerSettings.isFlutter = false
            return routerSettings
        }
    }

    override fun toString(): String {
        return if (isFlutter) {
            "Flutter url=$url, params=$params"
        } else {
            "Native  activity=${activity?.javaClass?.canonicalName}"
        }
    }


}
