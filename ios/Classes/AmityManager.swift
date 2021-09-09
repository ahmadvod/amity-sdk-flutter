//
//  AmityManager.swift
//  amity_sdk_flutter
//
//  Created by Shariq Ansari on 08/09/2021.
//

import Foundation
import AmitySDK

final class AmityManager {
    
    private(set) var client: AmityClient?
    
    var postRepository: AmityPostRepository?
    
    static let shared: AmityManager = AmityManager()
    
    func setup() {
        guard let apiKey: String = UserDefaults.standard.currentApiKey else {
            assertionFailure("API Key not found")
            return
        }
        
        if !UserDefaults.standard.isStagingEnvironment, let customHttpEndpoint = UserDefaults.standard.customHttpEndpoint, let customSocketEndpoint = UserDefaults.standard.customSocketEndpoint, !customHttpEndpoint.isEmpty, !customSocketEndpoint.isEmpty {
            
            self.client = AmityClient(apiKey: apiKey, httpUrl: customHttpEndpoint, socketUrl: customSocketEndpoint)
            Log.add(info: "AmityClient setup with custom endpoints: http: \(customHttpEndpoint), socket: \(customSocketEndpoint)")
            
        } else {
            self.client = AmityClient(apiKey: apiKey)
            Log.add(info: "AmityClient setup with default endpoints")
        }
        
        guard let client = client else {
            assertionFailure("client must not be nil at this point.")
            return
        }
        
        postRepository = AmityPostRepository(client: client)
        
    }
    
}
