package com.example.amity_sdk_flutter

import android.util.Log
import com.amity.socialcloud.sdk.social.AmitySocialClient
import com.example.amity_sdk_flutter.helper.AppConstant
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommunityPlugin: FlutterPlugin, MethodCallHandler {

    private lateinit var communityChannel : MethodChannel

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel = MethodChannel(binding.binaryMessenger, AppConstant.COMMUNITY_CHANNEL_NAME_KEY)
        communityChannel.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        communityChannel.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        Log.d("response_status", "in -> onMethodCall")
        when(call.method) {
            "createCommunity" -> {
                createCommunity()
            } else -> {
                result.notImplemented()
            }
        }
    }

    fun createCommunity() {
        val communityRepository = AmitySocialClient.newCommunityRepository()
        communityRepository
            .createCommunity("community1")
            .isPublic(true)
            .description("hello and welcome!")
            .categoryIds(listOf("new"))
            .userIds(listOf("user1", "user2"))
            .build()
            .create()
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                Log.d("response_status", "success")
            }.doOnError {
                Log.d("response_status", "error")
            }
            .subscribe()
    }
}