package io.flutter.embedding.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.cheney.flutter.pink.pink_router.PinkRouterWrapper
import cn.cheney.flutter.pink.pink_router.container.ContainerDelegate
import cn.cheney.flutter.pink.pink_router.util.Logger


class PinkFlutterActivity : FlutterActivity() {

    private var pinkDelegate: ContainerDelegate = ContainerDelegate(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinkDelegate.onCreate()
        PinkRouterWrapper.syncPush(url(), params())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        PinkRouterWrapper.syncPush(url(), params())
    }

    override fun onStart() {
        super.onStart()
        pinkDelegate.onStart()
    }

    override fun onResume() {
        super.onResume()
        pinkDelegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        pinkDelegate.onPause()
    }

    override fun onStop() {
        super.onStop()
        pinkDelegate.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        pinkDelegate.onDestroy()
    }

    companion object {
        const val KEY_URL = "url"
        const val KEY_PARAMS = "params"
        const val KEY_INDEX = "index"
        const val KEY_CONTAINER_ID = "containerId"
        const val KEY_INIT_ROUTER = FlutterActivityLaunchConfigs.EXTRA_INITIAL_ROUTE

        fun newIntent(context: Context,
                      url: String,
                      params: Map<String, Any?>?,
                      containerId: Long,
                      index: Int): Intent {
            val intent = Intent(context, PinkFlutterActivity::class.java)
            intent.putExtra(FlutterActivityLaunchConfigs.EXTRA_CACHED_ENGINE_ID, "main")
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_INDEX, index)
            intent.putExtra(KEY_CONTAINER_ID, containerId)
            params?.let {
                intent.putExtra(KEY_PARAMS, it as HashMap)
            }
            return intent
        }
    }

    override fun onBackPressed() {
        Logger.d("onBackPressed")
        PinkRouterWrapper.pop()
    }
}

fun PinkFlutterActivity.params(): Map<String, Any?>? {
    return intent.getSerializableExtra(PinkFlutterActivity.KEY_PARAMS) as? Map<String, Any?>
}


fun PinkFlutterActivity.index(): Int {
    return intent.getIntExtra(PinkFlutterActivity.KEY_INDEX, -1)
}

fun PinkFlutterActivity.containerId(): Long {
    return intent.getLongExtra(PinkFlutterActivity.KEY_CONTAINER_ID, -1)
}

fun PinkFlutterActivity.url(): String {
    return intent.getStringExtra(PinkFlutterActivity.KEY_URL)
}

