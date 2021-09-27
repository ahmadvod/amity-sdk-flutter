import 'package:flutter/services.dart';

class ChatPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");

  static Future<String?> createConversationChannel(
      String userId, String displayName) async {
    final String? result = await _channel.invokeMethod(
        'createConversationChannel',
        <String, dynamic>{'user_id': userId, 'display_name': displayName});

    return result;
  }

  static Future<String?> getUserConversationChannels() async {
    final String? result =
        await _channel.invokeMethod('getUserConversationChannels');

    return result;
  }

  static Future<String?> getChannelMessages(
      String channelId
      ) async {
    String? result = "";
    try {
      result = await _channel.invokeMethod(
          'getChannelMessages',
          <String, dynamic>{'channel_id': channelId});

      return result;
    } on PlatformException catch(e) {
      print('Error = $e');
      return "";
    }
  }

  static Future<String?> registerUserDevice(String userId, String displayName) async {
    final String result = await _channel.invokeMethod(
      'registerUserDevice',
        <String, dynamic>{'user_id': userId, 'display_name': displayName});
    return result;
  }

  static Future<String?> sendTextMessage(
    String body, String channelId
      ) async {
    final String result = await _channel.invokeMethod(
      'sendTextMessage',
        <String, dynamic>{'msg_body': body, 'channel_id': channelId});
    return result;
  }
}
