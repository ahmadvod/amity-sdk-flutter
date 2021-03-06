package com.example.amity_sdk_flutter.helper

object MethodChannelConstants {
    //Channels Name
    final const val COMMUNITY_CHANNEL_NAME_KEY = "amityMainChannel"

    //Methods Name
    final const val REGISTER_APP_METHOD_NAME = "registerApp"
    final const val AUTH_USER_METHOD_NAME = "authenticateUser"

    final const val CREATE_COMMUNITY_METHOD_NAME = "createCommunity"
    final const val JOIN_COMMUNITY_METHOD_NAME = "joinCommunity"

    final const val CREATE_TEXT_POST_METHOD_NAME = "createTextPost"
    final const val CREATE_TEXT_IMAGE_POST_METHOD_NAME = "createImageWithTextPost"
    final const val GET_USER_POST_METHOD_KEY = "getUserPost"

    final const val SEND_REQUEST_METHOD_NAME = "sendRequest"
    final const val GET_FRIEND_REQUEST_LIST_METHOD_NAME = "getFriendRequestList"
    final const val ACCEPT_REQUEST_METHOD_NAME = "acceptRequest"
    final const val UNFRIEND_METHOD_NAME = "unfriend"
}