package com.example.amity_sdk_flutter

import com.example.amity_sdk_flutter.feature.*
import com.example.amity_sdk_flutter.helper.MethodChannelConstants
import com.example.amity_sdk_flutter.interfaces.RepositoryResponseListener
import com.example.amity_sdk_flutter.interfaces.ResponseType
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class AmityPlugin : FlutterPlugin, MethodCallHandler, RepositoryResponseListener {

    private lateinit var communityChannel: MethodChannel
    private lateinit var channelResult: MethodChannel.Result

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel = MethodChannel(
            binding.binaryMessenger,
            MethodChannelConstants.COMMUNITY_CHANNEL_NAME_KEY
        )
        communityChannel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        channelResult = result
        when (call.method) {
            MethodChannelConstants.REGISTER_APP_METHOD_NAME -> {
                result.success(AmityUser().registerApp(call.argument(AmityUser.API_KEY)!!))
            }

            MethodChannelConstants.AUTH_USER_METHOD_NAME -> {
                result.success(AmityUser().authenticateUser(call.argument(AmityUser.USER_ID_KEY)!!))
            }

            MethodChannelConstants.CREATE_COMMUNITY_METHOD_NAME -> {
                AmityCommunity(this).createCommunity(
                    call.argument<String>(AmityCommunity.COMMUNITY_NAME_KEY)!!,
                    call.argument<Boolean>(AmityCommunity.IS_PUBLIC_KEY)!!,
                    call.argument<String>(AmityCommunity.DESCRIPTION_KEY)!!,
                    call.argument<String>(AmityCommunity.CATEGORIES_IDS_KEY),
                    call.argument<String>(AmityCommunity.USER_IDS_KEY)
                )
            }

            MethodChannelConstants.JOIN_COMMUNITY_METHOD_NAME -> {
                AmityCommunity(this).joinCommunity(
                    call.argument<String>(AmityCommunity.COMMUNITY_NAME_KEY)!!
                )
            }

            MethodChannelConstants.CREATE_TEXT_IMAGE_POST_METHOD_NAME -> {
                AmityPost(this).createImageWithTextPost(
                    call.argument(AmityPost.POST_TEXT_KEY)!!,
                    call.argument(AmityPost.POST_TYPE_KEY)!!,
                    call.argument(AmityPost.ID_KEY)!!,
                    call.argument(AmityPost.IMAGE_URL_KEY)!!
                )
            }

            MethodChannelConstants.GET_USER_POST_METHOD_KEY -> {
                AmityPost(this).getUserPosts(
                    call.argument(AmityPost.ID_KEY)!!
                )
            }

            MethodChannelConstants.CREATE_TEXT_POST_METHOD_NAME -> {
                AmityPost(this).createTextPost(
                    call.argument<String>(AmityPost.POST_TEXT_KEY)!!,
                    call.argument<String>(AmityPost.POST_TYPE_KEY)!!,
                    call.argument<String>(AmityPost.ID_KEY)!!
                )
            }

            MethodChannelConstants.SEND_REQUEST_METHOD_NAME -> {
                AmityConnections(this).sendRequest(
                    call.argument<String>(AmityConnections.ID_KEY)!!
                )
            }
            
            MethodChannelConstants.GET_FRIEND_REQUEST_LIST_METHOD_NAME -> {
                AmityConnections(this).getFriendRequestList()
            }

            MethodChannelConstants.ACCEPT_REQUEST_METHOD_NAME -> {
                AmityConnections(this).acceptRequest(
                    call.argument<String>(AmityConnections.ID_KEY)!!
                )
            }

            MethodChannelConstants.UNFRIEND_METHOD_NAME -> {
                AmityConnections(this).unfriend(
                    call.argument<String>(AmityConnections.ID_KEY)!!
                )
            }

            MethodChannelConstants.CREATE_CONVERSATION_CHANNEL_METHOD_NAME -> {
                AmityChat(this).createConversationChannel(
                    call.argument<String>(AmityChat.USER_ID_KEY)!!,
                    call.argument<String>(AmityChat.DISPLAY_NAME)!!
                )
            }

            MethodChannelConstants.SEND_TEXT_MSG_METHOD_NAME -> {
                AmityChat(this).sendTextMessage(
                    call.argument<String>(AmityChat.MSG_TXT_KEY)!!,
                    call.argument<String>(AmityChat.CHANNEL_ID_KEY)!!
                )
            }

            MethodChannelConstants.GET_CHANNEL_MSGS_METHOD_NAME -> {
                AmityChat(this).getChannelMessages(
                    call.argument<String>(AmityChat.CHANNEL_ID_KEY)!!
                )
            }

            MethodChannelConstants.GET_USER_CONVERSATION_CHANNELS_METHOD_NAME -> {
                AmityChat(this).getUserConversationChannels()
            }

            MethodChannelConstants.REGISTER_FOR_NEW_MSG_METHOD_KEY -> {
                AmityChat(this).getNewMessage(
                    call.argument<String>(AmityChat.CHANNEL_ID_KEY)!!
                )
            }

            else -> {
                result.notImplemented()
            }
        }
    }

    override fun onSuccess(extra: String, msg: String, responseType: ResponseType) {
        channelResult.success(msg)
    }

    override fun onError(extra: String, error: String?) {
        channelResult.success(error)
    }
}