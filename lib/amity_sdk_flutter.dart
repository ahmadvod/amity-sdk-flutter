
import 'dart:async';

import 'package:flutter/services.dart';

class AmitySdkFlutter {
  static const MethodChannel _channel =
      const MethodChannel('amity_sdk_flutter');

  static Future<String?> get platformVersion async {
    final String? version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<String?> registerApp(String apiKey) async {
    final String? registereResult = await _channel.invokeMethod('registerApp', {"apiKey": apiKey});
    return registereResult;
  }

  static Future<String?> authenticateUser(String userId) async {
    final String? registereResult = await _channel.invokeMethod('authenticateUser', {"userId": userId});
    return registereResult;
  }


}
