package com.example.amity_sdk_flutter_example

import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import android.os.Build
import android.widget.TextView
import com.amity.socialcloud.sdk.chat.channel.AmityChannel
import com.amity.socialcloud.sdk.chat.message.AmityMessage
import com.example.amity_sdk_flutter.feature.AmityChat
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.example.amity_sdk_flutter.model.AmityChannelObj
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class MainActivity: FlutterActivity() {
    lateinit var msgClickTxt: TextView
    lateinit var channelClickTxt: TextView
    lateinit var msgzClickTxt: TextView
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

    //    one channelID = 3a2dfb1d31dffb243c2b5b34838936d2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        msgClickTxt = findViewById(R.id.msgClickTxt)
        channelClickTxt = findViewById(R.id.channelClickTxt)
        msgzClickTxt = findViewById(R.id.msgzClickTxt)

//        AmityCoreClient.registerDevice("pixel")//change this to user_id while implementing
//            .displayName("pixi dev")
//            .build()
//            .submit()

        val obj = AmityChat(object: RepositoryResponseListener {
            override fun onSuccess(extra: String, msg: String, responseType: ResponseType) {
                when(responseType) {
                    ResponseType.SEND_TEXT_MSG -> {
                        LogUtil.log("Msg Sent")
                    }

                    ResponseType.CHANNEL_COMPLETE_MSGS -> {
                        val myType = object : TypeToken<List<AmityMessage>>() {}.type
                        val msgList = Gson().fromJson<List<AmityMessage>>(msg, myType)
                        if(msgList != null && msgList.isNotEmpty()) {
                            LogUtil.log("Channel Complete Msgz -> ${msgList.get(0).getData().toString()}")
                        }
                    }

                    ResponseType.CREATE_CONVERSATION_CHANNEL -> {
                        val amityChannel = Gson().fromJson(msg, AmityChannel::class.java)
                        LogUtil.log("Created Channel id is -> ${amityChannel.getChannelId()}")
                    }

                    ResponseType.USER_ALL_CONVERSATION_CHANNELS -> {
                        val myType = object : TypeToken<List<AmityChannelObj>>() {}.type
                        val channelList = Gson().fromJson<List<AmityChannelObj>>(msg, myType)
                        if(channelList != null && channelList.isNotEmpty()) {
                            LogUtil.log("User All Channel Id Is -> ${channelList.get(0).channelId}")
                        }
                    }

                    ResponseType.RECEIVE_NEW_MSG -> {
                        val myType = object : TypeToken<List<AmityMessage>>() {}.type
                        val msgList = Gson().fromJson<List<AmityMessage>>(msg, myType)
                        if(msgList != null && msgList.isNotEmpty()) {
                            LogUtil.log("New Msg Received -> ${msgList.get(0).getData().toString()}")
                        }
                    }
                }
            }

            override fun onError(extra: String, error: String?) {
                LogUtil.log("onError -> ${error!!}")
            }

        })

        msgClickTxt.setOnClickListener {
            obj.sendTextMessage("${getDeviceName()} _${Random(10).nextInt()}", "0a2aea6b17146af137c64c7d74dc03f9")
        }

        channelClickTxt.setOnClickListener {
            obj.getUserConversationChannels()
        }

        msgzClickTxt.setOnClickListener {
            obj.getChannelMessages("0a2aea6b17146af137c64c7d74dc03f9")
        }
//        obj.createConversationChannel("techno", "")
//        obj.joinChannel("0ada304cec29b19aede7b6eb5497d8ba")
//        obj.sendTextMessage("${getDeviceName()} _${Random(100).nextInt()}", "3a2dfb1d31dffb243c2b5b34838936d2")
//        obj.getChatClient("diff", this)
//        obj.getMessages()
//        obj.getUserConversationChannels()
//        obj.getNewMessage("0a2aea6b17146af137c64c7d74dc03f9")
        LogUtil.log(getDeviceName())
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
