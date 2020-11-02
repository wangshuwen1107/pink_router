package cn.cheney.flutter.pink.pink_router

import android.util.Log

class Logger {

    companion object {

        private const val TAG = "Pink"

        fun d(msg: String) {
            Log.d(TAG, msg)
        }
    }
}