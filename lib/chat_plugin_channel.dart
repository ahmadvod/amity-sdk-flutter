import 'package:flutter/services.dart';

class ChatPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");

  static Future<String?> createConversationChannel(
      String userId, String displayName
      ) async {
    final String? result = await _channel.invokeMethod(
        'createConversationChannel',
        <String, dynamic>{'user_id': userId, 'display_name': displayName});

    return result;
  }

  static Future<String?> getUserConversationChannels() async {
    final String? result = await _channel.invokeMethod(
      'getUserConversationChannels'
    );

    return result;
  }
}
