package cn.cheney.flutter.pink.pink_router.util

import android.util.Log

class Logger {

    companion object {

        private const val TAG = "Pink"

        public fun d(msg: String) {
            Log.d(TAG, msg)
        }
    }
}