package com.example.amity_sdk_flutter.feature

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amity.socialcloud.sdk.AmityCoreClient
import com.amity.socialcloud.sdk.chat.AmityChatClient
import com.amity.socialcloud.sdk.chat.message.AmityMessage
import com.example.amity_sdk_flutter.helper.LogUtil
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.example.amity_sdk_flutter.model.AmityChannelObj
import com.example.amity_sdk_flutter.model.AmityMessageObj
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
            }, {
                repositoryResponseListener.onError("", it.message)
//                LogUtil.log(LOG_VAL, it.message!!)
            })

//        LogUtil.log(LOG_VAL, "Sending data is $txt and $channelId")
    }

    fun sendImageMessage(imgPath: String, channelId: String) {
        messageRepository.createMessage(channelId)
            .with()
            .image(Uri.parse(imgPath))
//            .caption("It's a beautiful day")
            .isFullImage(true)
            .build()
            .send()
            .subscribe()
    }

    fun sendVideoMessage(vdoPath: String, channelId: String) {
        messageRepository.createMessage(channelId)
            .with()
            .file(Uri.parse(vdoPath))
//            .caption("It's a beautiful day")
            .build()
            .send()
            .subscribe()
    }

    fun getChannelMessages(channelId: String) {
        LogUtil.log("Received Channel Id -> $channelId")
        messageRepository.getMessages(channelId)
            .stackFromEnd(true)
            .build()
            .query()
            .subscribe ({
                if (it.size > 0) {
                    val list = ArrayList<AmityMessageObj>()
                    it.forEachIndexed { position, obj ->
                        val msgObj = AmityMessageObj(
                            obj.getChannelId(),
                            getMsgBody(obj.getDataType().name, obj.getData()),
                            obj.getMessageId(),
                            obj.getDataType().name,
                            obj.getCreatedAt().millis,
                            obj.getUserId()
                        )

                        list.add(msgObj)
                    }
                    repositoryResponseListener.onSuccess("", Gson().toJson(list), ResponseType.CHANNEL_COMPLETE_MSGS)
                }
            },{
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
                val obj = AmityChannelObj(
                    it!!.getChannelId(),
                    it!!.getDisplayName(),
                    it!!.getMemberCount(),
                    it!!.getMessageCount()
                )
                repositoryResponseListener.onSuccess(
                    "",
                    Gson().toJson(obj),
                    ResponseType.CREATE_CONVERSATION_CHANNEL
                )
            }, {
                repositoryResponseListener.onError("", it.message)
            })
    }

    fun getUserConversationChannels() {
        channelRepository.getChannels()
            .conversationType()
            .build()
            .query()
            .subscribe { it ->
                if (it.size > 0) {
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
                    repositoryResponseListener.onSuccess(
                        "",
                        Gson().toJson(list),
                        ResponseType.USER_ALL_CONVERSATION_CHANNELS
                    )
//                        LogUtil.log(LOG_VAL, "${list.size}")
                }
            }
        /*, {
                        repositoryResponseListener.onError("", it.message)
    //                    LogUtil.log(LOG_VAL, "failed")
                    }*/
    }

    fun registerUserDevice(userId: String, displayName: String) {
        AmityCoreClient.registerDevice(userId)
            .displayName(displayName)
            .build()
            .submit()
    }

    fun getNewMessage(channelId: String) {
        getMessageCollection(channelId)
    }

    fun getMessageCollection(channelId: String)/*: LiveData<PagedList<AmityMessage>>*/ {
        val flowableStream: Flowable<PagedList<AmityMessage>> = messageRepository.getMessages(channelId)
            .parentId(null)
            .stackFromEnd(true)
            .build()
            .query()

            flowableStream.subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("myNewMsg -> ", "${it.size}")
                }, {
                    Log.d("myNewMsg -> ", it.message!!)
                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnNext {
//                    Log.d("myNewMsg -> ", "${it.size}")
//                }.doOnError {
//                    Log.d("myNewMsg -> ", it.message!!)
//                }.subscribe()

//        return LiveDataReactiveStreams.fromPublisher(
//            messageRepository.getMessages(channelId)
//                .parentId(null)
//                .stackFromEnd(true)
//                .build()
//                .query()
//        )
//        LiveDataReactiveStreams.fromPublisher(
//            messageRepository.getMessages(channelId)
//                .parentId(null)
//                .stackFromEnd(true)
//                .build()
//                .query()
//
////                .doOnNext {
////                    if(it.size > 0){
////                        val list = ArrayList<AmityMessageObj>()
////                        it.forEachIndexed { position, obj ->
////                            val msgObj = AmityMessageObj(
////                                obj.getChannelId(),
////                                getMsgBody(obj.getDataType().name, obj.getData()),
////                                obj.getMessageId(),
////                                obj.getDataType().name,
////                                obj.getCreatedAt().millis,
////                                obj.getUserId()
////                            )
////
////                            list.add(msgObj)
////                        }
////                        Log.d("myNewMsg -> ", Gson().toJson(list))
////                        repositoryResponseListener.onSuccess("", Gson().toJson(list), ResponseType.RECEIVE_NEW_MSG)
////                    }
////                }
////                .doOnError {
////                    repositoryResponseListener.onError("", it.message)
////                }
//        )
    }

    companion object {
        const val CHANNEL_ID_KEY = "channel_id"
        const val USER_ID_KEY = "user_id"
        const val MSG_TXT_KEY = "msg_body"
        const val DISPLAY_NAME = "display_name"
        const val IMG_PATH = "img_path"
        const val VDO_PATH = "vdo_path"
    }

    fun getMsgBody(msgType: String, msgData: AmityMessage.Data): String? {
        when (msgType) {
            "TEXT" -> {
                return (msgData as AmityMessage.Data.TEXT).getText()
            }

            "IMAGE" -> {
                return (msgData as AmityMessage.Data.IMAGE).getImage()?.getUrl()!!
            }

            "FILE" -> {
                return (msgData as AmityMessage.Data.FILE).getFile()?.getUrl()!!
            }

            else ->
                return ""
        }
    }
}