package com.example.amity_sdk_flutter.helper

object MethodChannelConstants {
    //Channels Name
    const val COMMUNITY_CHANNEL_NAME_KEY = "amityMainChannel"

    //Methods Name
    const val REGISTER_APP_METHOD_NAME = "registerApp"
    const val AUTH_USER_METHOD_NAME = "authenticateUser"

    const val CREATE_COMMUNITY_METHOD_NAME = "createCommunity"
    const val JOIN_COMMUNITY_METHOD_NAME = "joinCommunity"

    const val CREATE_TEXT_POST_METHOD_NAME = "createTextPost"
    const val CREATE_TEXT_IMAGE_POST_METHOD_NAME = "createImageWithTextPost"
    const val GET_USER_POST_METHOD_KEY = "getUserPost"

    const val SEND_REQUEST_METHOD_NAME = "sendRequest"
    const val GET_FRIEND_REQUEST_LIST_METHOD_NAME = "getFriendRequestList"
    const val ACCEPT_REQUEST_METHOD_NAME = "acceptRequest"
    const val UNFRIEND_METHOD_NAME = "unfriend"
    
    const val SEND_TEXT_MSG_METHOD_NAME = "sendTextMessage"
    const val GET_CHANNEL_MSGS_METHOD_NAME = "getChannelMessages"
    const val CREATE_CONVERSATION_CHANNEL_METHOD_NAME = "createConversationChannel"
    const val GET_USER_CONVERSATION_CHANNELS_METHOD_NAME = "getUserConversationChannels"
    const val REGISTER_FOR_NEW_MSG_METHOD_KEY = "getNewMessage"
    const val REGISTER_USER_DEVICE_METHOD_KEY = "registerUserDevice"
    const val SEND_PHOTO_MSG_METHOD_NAME = "sendImageMessage"
    const val SEND_VIDEO_MSG_METHOD_NAME = "sendVideoMessage"

    const val CREATE_NEW_GROUP_CHAT_CHANNEL_METHOD_NAME = "createNewGroup"
    const val JOIN_GROUP_CHAT_CHANNEL_METHOD_NAME = "joinGroup"
    const val GROUP_CHAT_CHANNEL_MEMBERS_METHOD_NAME = "getChannelMembers"
    const val GROUP_CHAT_CHANNEL_DETAIL_METHOD_NAME = "getChannelDetail"
}