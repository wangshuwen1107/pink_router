package cn.cheney.flutter.pink.router.util

import android.util.Log

class Logger {

    companion object {

        private const val TAG = "Pink"

        fun d(msg: String) {
            Log.d(TAG, msg)
        }


        fun e(msg: String) {
            Log.e(TAG, msg)
        }
    }
}