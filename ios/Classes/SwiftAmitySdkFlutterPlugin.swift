import Flutter
import UIKit
import AmitySDK


public class SwiftAmitySdkFlutterPlugin: NSObject, FlutterPlugin {

    var client: AmityClient?


  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "amity_sdk_flutter", binaryMessenger: registrar.messenger())
    let instance = SwiftAmitySdkFlutterPlugin()
    registrar.addMethodCallDelegate(instance, channel: channel)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    if(call.method == "getPlatformVersion"){
        result("iOS " + UIDevice.current.systemVersion)
    }else if(call.method == "registerApp"){
        let params = call.arguments as! NSDictionary
        client = AmityClient(apiKey: params["apiKey"] as! String)
    }else if(call.method == "authenticateUser"){
        let params = call.arguments as! NSDictionary
        if(client != nil){
            client?.registerDevice(withUserId: params["userId"] as! String, displayName: params["userName"] as? String, authToken: nil, completion: { (success, error) in
                if(success){
                    result(true)
                }else{
                    result(false)
                }
            })
        }else{
            result(FlutterError(code: "SDKNotInititialized", message: "Amity SDK not initialized", details: "Please initialize SDK by calling registerApp method first."))
        }
    }else if(call.method == "signOut"){
        if(client != nil){
            client?.unregisterDevice()
            result(true)
        }else{
            result(FlutterError(code: "SDKNotInititialized", message: "Amity SDK not initialized", details: "Please initialize SDK by calling registerApp method first."))
        }
    }else if(call.method == "createCommunity"){
        let params = call.arguments as! NSDictionary
        let builder = AmityCommunityCreationDataBuilder()
        builder.setDisplayName(params["communityName"] as! String)
        builder.setIsPublic(true)
        if(client != nil){
            let communityRepository = AmityCommunityRepository.init(client: client!)
            communityRepository.createCommunity(with: builder) { (community, error) in
                if(error != nil){
                    result(true)
                }else{
                    result(false)
                }
            }
        }
    }else if(call.method == "joinCommunity"){
        let params = call.arguments as! NSDictionary

        let communityRepository = AmityCommunityRepository.init(client: client!)
        communityRepository.joinCommunity(withId: params["communityId"] as! String) { (success, error) in
            result(success)
        }
    }else if(call.method == "createTextPost"){
        let params = call.arguments as! NSDictionary
        let postBuilder = AmityTextPostBuilder()
        postBuilder.setText(params["postText"] as! String)
        let postRepository = AmityPostRepository.init(client: client!)
        postRepository.createPost(postBuilder, targetId: nil, targetType: AmityPostTargetType.community) { (post, error) in
            result(error != nil)
        }
    }else if(call.method == "createImageWithTextPost"){

    }
    
  }
}
