import 'package:flutter/services.dart';

class PostPluginChannel {
  static const MethodChannel _channel = const MethodChannel("amityMainChannel");

  static Future<String?> createTextPost(String id, String postText, {required PostType postType}) async {
    final String? result = await _channel.invokeMethod("createTextPost", <String, String>{
      'postText': postText,
      'id': id,
      'postType': postType.toString()
    });

    return result;
  }
}

enum PostType {
  TARGET_SELF, TARGET_USER, TARGET_COMMUNITY
}