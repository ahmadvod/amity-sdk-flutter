import 'dart:async';

import 'package:amity_sdk_flutter/chat_plugin_channel.dart';
import 'package:amity_sdk_flutter/community_plugin_channel.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Click Me!';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      // platformVersion = await AmitySdkFlutter.platformVersion ?? 'Unknown platform version';
      // platformVersion = await AmitySdkFlutter.
      platformVersion = await ChatPluginChannel.getChannelMessages(
              "0a2aea6b17146af137c64c7d74dc03f9") ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  void createComm() async {
    var categories = List<String>.filled(2, "uhu", growable: false);
    // categories.add("hello");
    // categories.add("test");
    var resu = await CommunityPluginChannel.createCommunity(
      "MyFirstNewComm", true, "No description",categories , null
    );
    print(resu);
    setState(() {
      if(resu != null) {
        _platformVersion = resu;
      } else {
        _platformVersion = "Something went wrong";
      }
    });
  }

  void regUser() async {
    await CommunityPluginChannel.authenticateUser("a@aa.ccc");
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: GestureDetector(onTap: (){
            regUser();
            },
            child: const Text('Plugin example app'),
          )
        ),
        body: Center(
          child: GestureDetector(
            onTap: () {
              createComm();
            },
            child: Text(_platformVersion),
          ),
        ),
      ),
    );
  }
}
