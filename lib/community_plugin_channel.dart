import 'package:amity_sdk_flutter/amity_sdk_flutter.dart';
import 'package:flutter/services.dart';

class CommunityPluginChannel {
  static const MethodChannel _channel = const MethodChannel("community_channel");

  static Future<String?> createCommunity() async {
    final String? result = await _channel.invokeMethod('createCommunity');
    return result;
  }
}