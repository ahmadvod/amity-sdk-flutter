package com.example.amity_sdk_flutter_example

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import android.os.Build
import android.util.Log
import com.amity.socialcloud.sdk.AmityCoreClient
import com.example.amity_sdk_flutter.feature.AmityChat
import com.example.amity_sdk_flutter.feature.LOG_VAL
import kotlin.random.Random

class MainActivity: FlutterActivity() {

    //how it works--->

    /**
     * first we hav to create channel with type conversation with method -> createChannel() and user
     * id is that user id with which we need to create conversation so userid will be other user id
     * then sendTextMessage() methiod is use to send message against channel and getMessages() method
     * is used to get all messages list from server related to specific channel and getUserConversationChannels()
     * this method is used to get all conversation channel created by that specific user and getNewMessage()
     * this method is use tto get live data change in current channel but livedataObserver in this method is to send data in
     * parent activity main method is AmityChat->getMessageCollection() so find to send data back to activity or
     * calling class without owner
     *
     * one thing remains is to set observer to get newly added message - DONE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        AmityCoreClient.registerDevice("huaweii")//change this to user_id while implementing
//            .displayName("huaweii dev")
//            .build()
//            .submit()

        val obj = AmityChat()
//        obj.joinChannel("0ada304cec29b19aede7b6eb5497d8ba")
//        obj.createChannel("techno")
//        obj.sendTextMessage("${getDeviceName()} _${Random(100).nextInt()}", "3a2dfb1d31dffb243c2b5b34838936d2")
//        obj.getChatClient("diff", this)
//        obj.getMessages()
//        obj.getUserConversationChannels()
        obj.getNewMessage(this)
//        Log.d(LOG_VAL, "${list.value?.size}")
    }

    fun getDeviceName(): String {
        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL
        return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
            capitalize(model)
        } else {
            capitalize(manufacturer) + " " + model
        }
    }


    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first).toString() + s.substring(1)
        }
    }
}
