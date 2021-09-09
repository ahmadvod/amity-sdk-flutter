import Flutter
import UIKit
import AmitySDK


public class SwiftAmitySdkFlutterPlugin: NSObject, FlutterPlugin {

    var client: AmityClient?
    
    var regions = ["Global","EU", "SG","US"]
    var regionEndpointDict: [String: String] = ["Global": AmityRegionalEndpoint.GLOBAL, "EU": AmityRegionalEndpoint.EU, "SG": AmityRegionalEndpoint.SG, "US": AmityRegionalEndpoint.US]


    public static func register(with registrar: FlutterPluginRegistrar) {
        let channel = FlutterMethodChannel(name: "amityMainChannel", binaryMessenger: registrar.messenger())
        let instance = SwiftAmitySdkFlutterPlugin()
        registrar.addMethodCallDelegate(instance, channel: channel)
    }

    public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
        let params = call.arguments as! NSDictionary
        if(call.method == "getPlatformVersion"){
            result("iOS " + UIDevice.current.systemVersion)
        }else if(call.method == "registerApp"){
            registerApp(apiKey: params["apiKey"] as! String)
        }else if(call.method == "authenticateUser"){
            login(userId: params["userId"] as! String, userName: params["userName"] as! String,
                  result: result)
        }else if(call.method == "signOut"){
            logOut()
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
    
    func registerApp(apiKey: String){
        
        UserDefaults.standard.currentApiKey = apiKey
        UserDefaults.standard.isStagingEnvironment =  true
        
        UserDefaults.standard.customHttpEndpoint = ""
        UserDefaults.standard.customSocketEndpoint = ""
        UserDefaults.standard.userId = ""
        
        UserDefaults.standard.synchronize()
        
        AmityManager.shared.setup()
        
    }
    
    
    func login(userId: String, userName: String, result: @escaping FlutterResult){
        let client: AmityClient = AmityManager.shared.client!
        client.registerDevice(withUserId: userId, displayName: userName, authToken: nil) { (isSuccess, error) in
            result(isSuccess)
        }
    }
    
    func logOut(){
        let client: AmityClient = AmityManager.shared.client!
        client.unregisterDevice();
    }
    
    
}
