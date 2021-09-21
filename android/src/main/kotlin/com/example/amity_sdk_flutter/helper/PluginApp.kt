package com.example.amity_sdk_flutter.helper

import android.app.Application
import android.util.Log
import androidx.annotation.NonNull
import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.AmityRegionalEndpoint
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging

class PluginApp: Application() {
    final val TAG = PluginApp::class.java.simpleName

    override fun onCreate() {
        super.onCreate()

        AmityCoreClient.setup(
            apiKey = AppConstant.AMITY_API_KEY
        )

        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(object : OnCompleteListener<String> {
                override fun onComplete(@NonNull task: Task<String>) {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException())
                        return
                    }

                    // Get new FCM registration token
                    val token: String? = task.getResult()

//                    AmityFCM
//                        .create()
//                        .setup(token)
//                        .subscribe()
//                    Log.d(TAG, token!!)
//                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                }
            })
    }
}