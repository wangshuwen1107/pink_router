package cn.cheney.flutter.pink.router.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import cn.cheney.flutter.pink.router.core.observer.PageObserverManager
import cn.cheney.flutter.pink.router.model.RouteSettings
import io.flutter.embedding.android.PinkActivity
import java.lang.ref.WeakReference
import java.util.*

object ActivityDelegate : Application.ActivityLifecycleCallbacks {

    private var activityStack: MutableList<WeakReference<Activity?>> = mutableListOf()

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
    }


    override fun onActivityDestroyed(activity: Activity) {
        if (activity !is PinkActivity) {
            PageObserverManager.destroy(RouteSettings.native(activity))
        }
        removeFromStack(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
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