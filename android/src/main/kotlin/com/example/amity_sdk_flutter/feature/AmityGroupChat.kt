package com.example.amity_sdk_flutter.feature

import com.amity.socialcloud.sdk.chat.AmityChatClient
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import com.google.gson.Gson

class AmityGroupChat(
    val repositoryResponseListener: RepositoryResponseListener
) {
    val channelRepository = AmityChatClient.newChannelRepository()

    fun createNewGroup(channelName: String) {
        channelRepository.createChannel()
            .liveType()
            .withDisplayName(channelName)
            .build()
            .create()
            .subscribe({
                repositoryResponseListener.onSuccess("created", Gson().toJson(it), ResponseType.GROUP_CHAT_CHANNEL_CREATED)
            },{
                repositoryResponseListener.onError("", it.message)
            })
    }

    fun joinGroup(channelId: String) {
        channelRepository.joinChannel(channelId)
            .subscribe()
    }

    fun getChannelMembers(channelId: String) {
        AmityChatClient.newChannelRepository()
            .membership(channelId)
            .getMembers()
            .build()
            .query()
            .subscribe({
                repositoryResponseListener.onSuccess("received", Gson().toJson(it), ResponseType.GROUP_CHAT_MEMBERS_LIST)
            },{
                repositoryResponseListener.onError("", it.message)
            })
    }

    fun getChannelDetail(channelId: String) {
        channelRepository.getChannel(channelId)
            .subscribe {
                repositoryResponseListener.onSuccess("detail_received", Gson().toJson(it), ResponseType.GROUP_CHANNEL_DETAIL)
            }
    }

    companion object {
        const val CHANNEL_ID_KEY = "channel_id"
    }
}