package cn.cheney.flutter.pink.pink_router.container

import android.content.Intent
import cn.cheney.flutter.pink.pink_router.Logger
import cn.cheney.flutter.pink.pink_router.PinkRouter
import io.flutter.embedding.android.PinkFlutterActivity

public class ContainerDelegate(private val activity: PinkFlutterActivity) {


    fun onCreate() {
        val urlStr = activity.intent.getStringExtra(PinkFlutterActivity.KEY_URL)
        val params = activity.intent.getSerializableExtra(PinkFlutterActivity.KEY_PARAMS)
                as? Map<String, Any>?
        PinkRouter.engine.sendChannel.push(urlStr, params) {
            Logger.d("Flutter-> Native url=$urlStr result=$it")
        }
    }


    fun onStart() {

    }


    fun onResume() {

    }


    fun onPause() {

    }

    fun onStop() {

    }


    fun onDestroy() {

    }


    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

    }


}