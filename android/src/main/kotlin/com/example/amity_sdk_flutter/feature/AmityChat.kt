package com.example.amity_sdk_flutter.feature

import android.net.Uri
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.chat.AmityChatClient
import com.amity.socialcloud.sdk.chat.message.AmityMessage
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.example.amity_sdk_flutter.model.AmityChannelObj
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.annotations.NotNull

//const val LOG_VAL = "MyLogStatus => "

class AmityChat(
    val repositoryResponseListener: RepositoryResponseListener
) {
    private val messageRepository = AmityChatClient.newMessageRepository()
    private val channelRepository = AmityChatClient.newChannelRepository()

    fun sendTextMessage(txt: String, channelId: String) {
        messageRepository.createMessage(channelId)
            .with()
            .text(txt)
            .build()
            .send()
            .subscribe({
                repositoryResponseListener.onSuccess("Sent", "", ResponseType.SEND_TEXT_MSG)
//                LogUtil.log(LOG_VAL, "done")
            },{
                repositoryResponseListener.onError("", it.message)
//                LogUtil.log(LOG_VAL, it.message!!)
            })

//        LogUtil.log(LOG_VAL, "Sending data is $txt and $channelId")
    }

    fun sendImageMessage(imgPath: String, channelId: String) {
        messageRepository.createMessage(channelId)
            .with()
            .image(Uri.parse(imgPath))
            .caption("It's a beautiful day")
            .isFullImage(true)
            .build()
            .send()
            .subscribe()
    }

    fun getChannelMessages(channelId: String) {
            messageRepository.getMessages(channelId)
            .stackFromEnd(true)
            .build()
            .query()
            .subscribe({
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.CHANNEL_COMPLETE_MSGS)
//                LogUtil.log(LOG_VAL, "${it.size}")
            }, {
                repositoryResponseListener.onError("", it.message)
            })
    }

    fun createConversationChannel(userId: String, displayName: String) {
        val channelCreator = channelRepository.createChannel()
            .conversationType()
            .withUserId(userId)

        channelCreator.displayName(displayName)

        channelCreator.build()
            .create()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.CREATE_CONVERSATION_CHANNEL)
//                LogUtil.log(LOG_VAL, "created")
            }, {
                repositoryResponseListener.onError("", it.message)
//                LogUtil.log(LOG_VAL, "failed")
            })
    }

    fun getUserConversationChannels() {
//        AmityCoreClient.registerDevice("pixelNew")//change this to user_id while implementing
//            .displayName("pixi new dev")
//            .build()
//            .submit()

        channelRepository.getChannels()
            .conversationType()
            .build()
            .query()
            .subscribe {it ->
                if(it.size > 0) {
                    val list = ArrayList<AmityChannelObj>()
                    for (v in 0..it.size - 1) {
                        val channelObj = it.get(v)!!
                        val obj = AmityChannelObj(
                            channelObj.getChannelId(),
                            channelObj.getDisplayName(),
                            channelObj.getMemberCount(),
                            channelObj.getMessageCount()
                        )
                        list.add(obj)
                    }
                    repositoryResponseListener.onSuccess("", Gson().toJson(list), ResponseType.USER_ALL_CONVERSATION_CHANNELS)
//                        LogUtil.log(LOG_VAL, "${list.size}")
                }
            }
    /*, {
                    repositoryResponseListener.onError("", it.message)
//                    LogUtil.log(LOG_VAL, "failed")
                }*/
    }

    fun getNewMessage(channelId: String){
        getMessageCollection(channelId)
    }

    fun getMessageCollection(channelId: String): LiveData<PagedList<AmityMessage>> {
        return LiveDataReactiveStreams.fromPublisher(
            messageRepository.getMessages(channelId)
                .parentId(null)
                .stackFromEnd(true)
                .build()
                .query()
                .doOnNext {
                    repositoryResponseListener.onSuccess("", Gson().toJson(it), ResponseType.RECEIVE_NEW_MSG)
                }
                .doOnError {
                    repositoryResponseListener.onError("", it.message)
                }
        )
    }

    companion object {
        const val CHANNEL_ID_KEY = "channel_id"
        const val USER_ID_KEY = "user_id"
        const val MSG_TXT_KEY = "msg_text_key"
        const val DISPLAY_NAME = "display_name"
    }
}