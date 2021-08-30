import 'dart:async';

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
  String _platformVersion = 'Unknown';

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
      platformVersion = await CommunityPluginChannel.registerApp(
              "b0ede85a388aa6634c638f4e535b178ad40a84b1ee316828") ??
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
    var categories = List<String>.filled(2, "", growable: false);
    categories.add("hello");
    categories.add("test");
    await CommunityPluginChannel.createCommunity(
      "MyFirstNewComm", true, "No description",categories , null
    );
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
            child: Text("Click Me"),
          ),
        ),
      ),
    );
  }
}
