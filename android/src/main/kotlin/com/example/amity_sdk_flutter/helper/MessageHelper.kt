package com.example.amity_sdk_flutter.helper

import com.google.gson.Gson
import org.json.JSONObject

object MessageHelper {
    fun generateErrorMessage(error: String): String {
        val jObj = JSONObject()
        jObj.put(AppConstant.ERROR_KEY, error)
        return Gson().toJson(jObj)
    }

    fun generateSuccessMessage(msg: String): String {
        val jObj = JSONObject()
        jObj.put(AppConstant.SUCCESS_KEY, msg)
        return Gson().toJson(jObj)
    }
}