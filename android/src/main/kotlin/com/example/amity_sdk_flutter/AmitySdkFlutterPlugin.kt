package com.example.amity_sdk_flutter

import androidx.annotation.NonNull
import com.amity.socialcloud.sdk.AmityCoreClient
import com.example.amity_sdk_flutter.helper.AppConstant
import com.example.amity_sdk_flutter.helper.MessageHelper

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import org.jetbrains.annotations.NotNull

/** AmitySdkFlutterPlugin */
class AmitySdkFlutterPlugin: FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "amity_sdk_flutter")
    channel.setMethodCallHandler(this)
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      result.success("Android ${android.os.Build.VERSION.RELEASE}")
    } else if(call.method == "registerApp") {
      result.success(registerApp(call.argument("apiKey")!!))
    } else if(call.method == "authenticateUser") {
      result.success(authenticateUser(call.argument("userId")!!))
    }
    else {
      result.notImplemented()
    }
  }

  fun registerApp(@NotNull apiKey: String): String? {
    AmityCoreClient.setup(apiKey)

    return MessageHelper.generateSuccessMessage("Api key added")
  }

  fun authenticateUser(@NotNull userId: String, displayName: String? = ""): String? {
    AmityCoreClient.registerDevice(userId).displayName(displayName ?: "").build().submit()

    return MessageHelper.generateSuccessMessage("Device Registered")
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }


}
