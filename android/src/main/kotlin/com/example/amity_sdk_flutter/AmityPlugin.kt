package com.example.amity_sdk_flutter

import com.example.amity_sdk_flutter.feature.AmityCommunity
import com.example.amity_sdk_flutter.feature.AmityUser
import com.example.amity_sdk_flutter.helper.MethodChannelConstants
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler

class AmityPlugin: FlutterPlugin, MethodCallHandler {

    private lateinit var communityChannel : MethodChannel

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel = MethodChannel(binding.binaryMessenger, MethodChannelConstants.COMMUNITY_CHANNEL_NAME_KEY)
        communityChannel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method) {
            MethodChannelConstants.CREATE_COMMUNITY_METHOD_NAME -> {
                result.success(AmityCommunity().createCommunity(
                    call.argument<String>(AmityCommunity.COMMUNITY_NAME_KEY)!!,
                    call.argument<Boolean>(AmityCommunity.IS_PUBLIC_KEY)!!,
                    call.argument<String>(AmityCommunity.DESCRIPTION_KEY)!!,
                    call.argument<List<String>>(AmityCommunity.CATEGORIES_IDS_KEY),
                    call.argument<List<String>>(AmityCommunity.USER_IDS_KEY)
                ))
            }

            MethodChannelConstants.REGISTER_APP_METHOD_NAME -> {
                result.success(AmityUser().registerApp(call.argument(AmityUser.API_KEY)!!))
            }
            MethodChannelConstants.AUTH_USER_METHOD_NAME -> {
                result.success(AmityUser().authenticateUser(call.argument(AmityUser.USER_ID_KEY)!!))
            }
            else -> {
                result.notImplemented()
            }
        }
    }
}