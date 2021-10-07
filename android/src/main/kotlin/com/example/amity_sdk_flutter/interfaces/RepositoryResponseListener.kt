package com.example.amity_sdk_flutter.interfaces

interface RepositoryResponseListener {
    fun onSuccess(extra: String, msg: String, responseType: ResponseType)
    fun onError(extra: String, error: String?)
}

enum class ResponseType {
    CREATE_COMMUNITY, JOIN_COMMUNITY, TEXT_POST, GET_PEER_FEEDS, IMAGE_TEXT_POST,
    FRIEND_REQUEST_SENT, FRIEND_REQUEST_LIST, FRIEND_REQUEST_ACCEPTED, FRIEND_REQUEST_DECLINE,
    CREATE_CONVERSATION_CHANNEL, SEND_TEXT_MSG, CHANNEL_COMPLETE_MSGS, USER_ALL_CONVERSATION_CHANNELS,
    RECEIVE_NEW_MSG, GROUP_CHAT_CHANNEL_CREATED, GROUP_CHAT_MEMBERS_LIST, GROUP_CHANNEL_DETAIL
}