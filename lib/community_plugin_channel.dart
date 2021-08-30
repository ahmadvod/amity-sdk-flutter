import 'package:flutter/services.dart';

class CommunityPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");

  static Future<String?> createCommunity(
    String communityName, bool isPublic, String description, List<String>? categoryIds, List<String>? userIds
      ) async {

    final String? result = await _channel.invokeMethod('createCommunity', <String, dynamic>{
      'communityName': communityName,
      'isPublic': isPublic,
      'description': description,
      'categoryIds': categoryIds?.join(","),
      'userIds': userIds?.join(","),
    });
    return result;
  }

  static Future<String?> registerApp(String apiKey) async {
    final String? registereResult =
        await _channel.invokeMethod('registerApp', {"apiKey": apiKey});
    return registereResult;
  }

  static Future<String?> authenticateUser(String userId) async {
    final String? registereResult =
        await _channel.invokeMethod('authenticateUser', {"userId": userId});
    return registereResult;
  }
}
