package io.flutter.embedding.android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import cn.cheney.flutter.pink.router.core.PinkRouterImpl
import cn.cheney.flutter.pink.router.container.ContainerDelegate
import cn.cheney.flutter.pink.router.util.Logger
import java.io.Serializable


class PinkActivity : PinkFlutterActivity() {

    private var pinkDelegate: ContainerDelegate = ContainerDelegate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pinkDelegate.onCreate()
        onPush(url(), params())
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        onPush(url(), params())
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


    override fun onBackPressed() {
        Logger.d("onBackPressed is called ")
        PinkRouterImpl.pop(null, true)
    }


    private fun onPush(url: String, params: Map<String, Any?>?) {
        PinkRouterImpl.engine.sendChannel.push(url, params)
    }

    companion object {
        const val KEY_URL = "url"
        const val KEY_PARAMS = "params"
        const val KEY_INDEX = "index"
        const val KEY_CONTAINER_ID = "containerId"
        const val KEY_INIT_ROUTER = FlutterActivityLaunchConfigs.EXTRA_INITIAL_ROUTE

        @JvmStatic
        fun newIntent(context: Context,
                      url: String,
                      params: Map<String, Any?>?,
                      containerId: Long,
                      index: Int): Intent {
            val intent = Intent(context, PinkActivity::class.java)
            intent.putExtra(FlutterActivityLaunchConfigs.EXTRA_CACHED_ENGINE_ID, "main")
            intent.putExtra(KEY_URL, url)
            intent.putExtra(KEY_INDEX, index)
            intent.putExtra(KEY_CONTAINER_ID, containerId)
            if (null != params && params is Serializable) {
                intent.putExtra(KEY_PARAMS, params)
            }
            return intent
        }
    }

}

fun PinkActivity.params(): Map<String, Any?>? {
    return intent.getSerializableExtra(PinkActivity.KEY_PARAMS) as? Map<String, Any?>
}


fun PinkActivity.index(): Int {
    return intent.getIntExtra(PinkActivity.KEY_INDEX, -1)
}

fun PinkActivity.containerId(): Long {
    return intent.getLongExtra(PinkActivity.KEY_CONTAINER_ID, -1)
}

fun PinkActivity.url(): String {
    return intent.getStringExtra(PinkActivity.KEY_URL) ?: ""
}

