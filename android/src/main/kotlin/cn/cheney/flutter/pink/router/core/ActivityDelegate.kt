package cn.cheney.flutter.pink.router.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import cn.cheney.flutter.pink.router.core.observer.PageObserverManager
import cn.cheney.flutter.pink.router.entity.RouteSettings
import cn.cheney.flutter.pink.router.util.Logger
import io.flutter.embedding.android.PinkActivity
import io.flutter.embedding.android.params
import io.flutter.embedding.android.url
import java.lang.ref.WeakReference

object ActivityDelegate : Application.ActivityLifecycleCallbacks {

    private var activityStack: MutableList<WeakReference<Activity?>> = mutableListOf()

    private var acStartCount = 0

    private var isFirstForeground = true

    private var isForeground = true

    fun getTopActivity(): Activity? {
        return activityStack.lastOrNull()?.get()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity !is PinkActivity) {
            PageObserverManager.create(RouteSettings.native(activity))
        }
        activityStack.add(WeakReference(activity))
    }

    override fun onActivityStarted(activity: Activity) {
        if (++acStartCount == 1) {
            Logger.d("LifeCycle -----> foreground")
            isForeground = true
            if (isFirstForeground) {
                isFirstForeground = false
            }
            onForeground(activity)
        }
        if (activity !is PinkActivity) {
            PageObserverManager.willAppear(RouteSettings.native(activity))
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (activity !is PinkActivity) {
            PageObserverManager.didAppear(RouteSettings.native(activity))
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (activity !is PinkActivity) {
            PageObserverManager.willDisappear(RouteSettings.native(activity))
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (activity !is PinkActivity) {
            PageObserverManager.didDisappear(RouteSettings.native(activity))
        }
        if (--acStartCount == 0) {
            isForeground = false
            Logger.d("LifeCycle -----> background")
            onBackground(activity)
        }
    }


    override fun onActivityDestroyed(activity: Activity) {
        if (activity !is PinkActivity) {
            PageObserverManager.destroy(RouteSettings.native(activity))
        }
        removeFromStack(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }


    private fun onBackground(activity: Activity) {
        if (activity is PinkActivity) {
            val routeSettings = RouteSettings.flutter(activity.url(), activity.params())
            PageObserverManager.willDisappear(routeSettings)
            PageObserverManager.didDisappear(routeSettings)
        }
    }

    private fun onForeground(activity: Activity) {
        if (!isFirstForeground && activity is PinkActivity) {
            val routeSettings = RouteSettings.flutter(activity.url(), activity.params())
            PageObserverManager.willAppear(routeSettings)
            PageObserverManager.didAppear(routeSettings)
        }
    }


    private fun removeFromStack(activity: Activity) {
        val removeActivity = activityStack.firstOrNull {
            activity == it.get()
        }
        removeActivity?.let {
            activityStack.remove(it)
        }
    }


    fun getPreviousActivity(): Activity? {
        if (activityStack.size < 2) {
            return null
        }
        return activityStack[activityStack.size - 2].get()
    }

}