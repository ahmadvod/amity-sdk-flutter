package com.example.amity_sdk_flutter.helper

import android.util.Log

const val LOG_KEY_DEF_VALUE = "AmityPluginLog -->  "
object LogUtil {

    fun logD(value: String,key: String? = LOG_KEY_DEF_VALUE) {
        if(value == null) return
        Log.d(key, value)
    }

    fun logE(value: String,key: String? = LOG_KEY_DEF_VALUE) {
        if(value == null) return
        Log.e(key, value)
    }

    fun logI(value: String,key: String? = LOG_KEY_DEF_VALUE){
        if(value == null) return
        Log.i(key, value)
    }

    fun log(value: String,key: String? = LOG_KEY_DEF_VALUE){
        if(value == null) return
        Log.v(key, value)
    }
}