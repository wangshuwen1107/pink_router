package cn.cheney.flutter.pink.pink_router

import android.content.Context
import cn.cheney.flutter.pink.pink_router.util.Logger
import io.flutter.embedding.android.PinkActivity
import io.flutter.embedding.android.containerId
import io.flutter.embedding.android.index
import io.flutter.embedding.android.url
import java.util.*


internal object PinkRouterWrapper {

    private lateinit var engine: PinkEngine

    var resultCallbackMap: MutableMap<String, ResultCallback> = mutableMapOf()

    var containerStack: Stack<Long> = Stack()

    var pageMap: MutableMap<Long, Stack<String>> = mutableMapOf()

    fun init(context: Context) {
        engine = PinkEngine(context)
    }


    fun syncPush(url: String, params: Map<String, Any?>? = null, callback: ResultCallback? = null) {
        engine.sendChannel.push(url, params, callback)
    }


    fun pop(result: Any? = null, isBackPress: Boolean = false) {
        val topActivity = NativeActivityRecord.getTopActivity()
        if (null == topActivity || topActivity !is PinkActivity) {
            return
        }
        val index = (topActivity).index()
        if (index == 0) {
            val prevKey = topActivity.url() + "_" + topActivity.index()
            engine.sendChannel.pop(result, isBackPress) {
                val popResult = it as? Boolean
                if (popResult != null && popResult) {
                    containerStack.remove(topActivity.containerId())
                    resultCallbackMap.remove(prevKey)?.invoke(result)
                    topActivity.finish()
                }
            }
        } else {
            engine.sendChannel.pop(result, isBackPress) {
                val popResult = it as? Boolean
                if (popResult != null && popResult) {
                    val containerPageStack = pageMap[topActivity.containerId()]
                    val prevKey = containerPageStack?.pop()
                    val prevUrl = prevKey?.split("_")?.get(0)
                    topActivity.intent.putExtra(PinkActivity.KEY_URL, prevUrl)
                    topActivity.intent.putExtra(PinkActivity.KEY_INDEX, index - 1)
                    resultCallbackMap.remove(prevKey)?.invoke(result)
                }
            }
        }
    }

    fun push(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        val topActivity = NativeActivityRecord.getTopActivity()
        if (null == topActivity) {
            callback?.invoke("error_context")
            return
        }
        val index: Int
        val containerId: Long
        val key: String
        if (topActivity is PinkActivity) {
            index = topActivity.index() + 1
            containerId = topActivity.containerId()

            if (containerStack.peek() != containerId) {
                containerStack.remove(containerId)
                containerStack.push(containerId)
            }
            var containerPageStack = pageMap[containerId]
            if (null == containerPageStack) {
                containerPageStack = Stack()
                pageMap[containerId] = containerPageStack
            }
            key = url + "_" + index
            containerPageStack.push(key)

        } else {
            index = 0
            key = url + "_" + index
            containerId = System.currentTimeMillis()

            containerStack.push(containerId)
            pageMap[containerId] = Stack()
            pageMap[containerId]?.push(key)

        }
        callback?.let {
            resultCallbackMap[key] = it
        }
        val finalMap: MutableMap<String, Any> = mutableMapOf()
        params?.let {
            finalMap.putAll(it)
        }
        finalMap["isNested"] = index != 0
        val intent = PinkActivity.newIntent(topActivity,
                url, finalMap, containerId, index)
        topActivity.startActivity(intent)
    }


    fun call(url: String, params: Map<String, Any>?, callback: ResultCallback?) {
        engine.sendChannel.call(url, params, callback)
    }


}