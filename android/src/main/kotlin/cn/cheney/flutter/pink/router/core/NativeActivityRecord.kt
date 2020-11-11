package cn.cheney.flutter.pink.router.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import java.lang.ref.WeakReference

object NativeActivityRecord {

    private var topActivity: WeakReference<Activity?>? = null

    fun registerCallbacks(context: Context) {
        (context.applicationContext as? Application)?.registerActivityLifecycleCallbacks(
                object : Application.ActivityLifecycleCallbacks {
                    override fun onActivityPaused(activity: Activity?) {
                    }

                    override fun onActivityResumed(activity: Activity?) {
                        topActivity = WeakReference(activity)
                    }

                    override fun onActivityStarted(activity: Activity?) {
                    }

                    override fun onActivityDestroyed(activity: Activity?) {
                    }

                    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
                    }

                    override fun onActivityStopped(activity: Activity?) {
                    }

                    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    }

                })
    }


    fun getTopActivity(): Activity? {
        return topActivity?.get()
    }


}