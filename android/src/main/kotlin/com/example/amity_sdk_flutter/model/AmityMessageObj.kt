package com.example.amity_sdk_flutter.model

data class AmityMessageObj(
    var channelId: String = "",
    var messageBody: String? = "",
    var messageId: String = "",
    var messageType: String = "",
    var createdAt: Long = 0,
    var userId: String = ""
)