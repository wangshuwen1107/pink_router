package cn.cheney.flutter.pink.pink_router

import io.flutter.plugin.common.MethodChannel

object RequestManager {


    var startCode: Int = 0

    private val requestMap by lazy {
        mutableMapOf<Int, MethodChannel.Result>()
    }


    fun generateRequestCode(result: MethodChannel.Result): Int {
        val requestCode = startCode++
        requestMap[requestCode] = result
        return  requestCode
    }


    fun setResult(requestCode: Int, result: Map<String, Any>?) {
        val resultCallback = requestMap.remove(requestCode)
        resultCallback?.success(result)
    }


}