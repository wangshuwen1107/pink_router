package io.flutter.embedding.android

import android.content.Intent
import android.os.Bundle
import cn.cheney.flutter.pink.pink_router.container.ContainerDelegate
import io.flutter.embedding.android.FlutterActivity


open class PinkFlutterActivity : FlutterActivity() {

    private lateinit var pinkDelegate: ContainerDelegate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinkDelegate = ContainerDelegate(this)
        pinkDelegate.onCreate()
    }

    companion object {
        const val KEY_URL = "url"
        const val KEY_PARAMS = "params"
        const val KEY_ENGINE_ID = FlutterActivityLaunchConfigs.EXTRA_CACHED_ENGINE_ID
    }

}