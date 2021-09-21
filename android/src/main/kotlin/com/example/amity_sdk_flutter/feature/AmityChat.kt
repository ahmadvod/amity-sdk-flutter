package com.example.amity_sdk_flutter.feature

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.paging.PagedList
import com.amity.socialcloud.sdk.chat.AmityChatClient
import com.amity.socialcloud.sdk.chat.AmityMessageRepository
import com.amity.socialcloud.sdk.chat.channel.AmityChannel
import com.amity.socialcloud.sdk.chat.message.AmityMessage
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

const val LOG_VAL = "MyLogStatus => "
class AmityChat {
    val messagesLiveData = MutableLiveData<AmityMessage>()
    val messageRepository = AmityChatClient.newMessageRepository()

    private val channelRepository = AmityChatClient.newChannelRepository()

    fun sendTextMessage(txt: String, channelId: String) {
        messageRepository.createMessage(channelId)
            .with()
            .text(txt)
            .build()
            .send()
            .subscribe({
                Log.d(LOG_VAL, "done")
            },{
                Log.d(LOG_VAL, it.message!!)
            })

        Log.d(LOG_VAL, "Sending data is $txt and $channelId")
//            .subscribe()
    }

    fun getChatClient(channelId: String, activity: LifecycleOwner) {
        LiveDataReactiveStreams.fromPublisher(AmityChatClient.newChannelRepository().getChannel(channelId)).observe(activity, Observer {
            if (it.getType() == AmityChannel.Type.BROADCAST) {
                Log.d(LOG_VAL, it.getDisplayName())
            }

            Log.d(LOG_VAL, it.getDisplayName())
        })
         messageRepository.getMessages("diff")
    }

    fun getMessages() {
            messageRepository.getMessages("3a2dfb1d31dffb243c2b5b34838936d2")
            .stackFromEnd(true)
            .build()
            .query()
            .subscribe( {
                Log.d(LOG_VAL, "${it.size}")
            } )
    }

    fun createChannel(userId: String) {
        val channelCreator = channelRepository.createChannel()
            .conversationType()
            .withUserId(userId)

        channelCreator.displayName("myDisplayName")

        channelCreator.build()
            .create()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(LOG_VAL, "created")
            }, {
                Log.d(LOG_VAL, "failed")
            })
    }

    fun getUserConversationChannels() {
        channelRepository.getChannels()
            .conversationType()
            .build()
            .query()
            .subscribe( {
                Log.d(LOG_VAL, "${it.size}")
            } )
    }

    //this function is not worki9ng
    fun getNewMessage(activity: LifecycleOwner){
//        val flowableStream: Flowable<AmityMessage> =
        val message = getMessageCollection()
        message.observe(activity, Observer {
            Log.d(LOG_VAL, "${it.toString()}")
        })
    }

    fun getMessageCollection(): LiveData<PagedList<AmityMessage>> {
        return LiveDataReactiveStreams.fromPublisher(
            messageRepository.getMessages("3a2dfb1d31dffb243c2b5b34838936d2")
                .parentId(null)
                .stackFromEnd(true)
                .build()
                .query()
        )
    }
}