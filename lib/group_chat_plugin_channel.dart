import 'package:flutter/services.dart';

class GroupChatPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");

  static Future<String?> createNewGroupChannel(String channelId) async {
    String result = await _channel.invokeMethod(
        'createNewGroup', <String, dynamic>{'channel_id': channelId});
    return result;
  }

  static Future<String?> joinGroup(String channelId) async {
    String result = await _channel.invokeMethod(
        'joinGroup', <String, dynamic>{'channel_id': channelId});
    return result;
  }

  static Future<String?> getChannelMembers(String channelId) async {
    String result = await _channel.invokeMethod(
        'getChannelMembers', <String, dynamic>{'channel_id': channelId});
    return result;
  }

  static Future<String?> getChannelDetail(String channelId) async {
    String result = await _channel.invokeMethod(
        'getChannelDetail', <String, dynamic>{'channel_id': channelId});
    return result;
  }
}
