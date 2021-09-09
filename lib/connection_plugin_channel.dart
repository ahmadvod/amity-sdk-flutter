import 'package:flutter/services.dart';

class ConnectionPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");
  
  static Future<String?> sendFriendRequest(
      String userId
      ) async {
    final String? result = await _channel.invokeMethod('sendRequest', <String, String> {
      'id': userId
    });

    return result;
  }

  static Future<String?> acceptRequest(
      String userId
      ) async {
    final String? result = await _channel.invokeMethod('acceptRequest', <String, String> {
      'id': userId
    });

    return result;
  }

  static Future<String?> unfriend(
      String userId
      ) async {
    final String? result = await _channel.invokeMethod('unfriend', <String, String> {
      'id': userId
    });

    return result;
  }

  static Future<String?> getFriendRequestList() async {
    final String? result = await _channel.invokeMethod('getFriendRequestList');

    return result;
  }
}