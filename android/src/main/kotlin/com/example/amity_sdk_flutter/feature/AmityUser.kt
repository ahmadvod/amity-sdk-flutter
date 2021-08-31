package com.example.amity_sdk_flutter.feature

import android.util.Log
import com.amity.socialcloud.sdk.AmityCoreClient
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.helper.MessageHelper
import org.jetbrains.annotations.NotNull

class AmityUser {

    fun registerApp(@NotNull apiKey: String): String? {
        AmityCoreClient.setup(apiKey)
        LogUtil.log("registerApp")
        return MessageHelper.generateSuccessMessage("Api key added successfully")
    }

    fun authenticateUser(@NotNull userId: String, displayName: String? = ""): String? {
        AmityCoreClient.registerDevice(userId).displayName(displayName ?: "").build().submit()
        LogUtil.log("authenticateUser")
        return MessageHelper.generateSuccessMessage("Device Registered")
    }

    companion object {
        const val API_KEY = "apiKey"
        const val USER_ID_KEY = "userId"
    }
}