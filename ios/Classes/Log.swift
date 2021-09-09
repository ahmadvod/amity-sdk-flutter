//
//  Log.swift
//  amity_sdk_flutter
//
//  Created by Shariq Ansari on 08/09/2021.
//

import Foundation

class Log {
    
    static var isEnabled = true
    
    // Prints on console on this format:
    // › [SampleApp]: [ViewController.methodName()] : My Log
    static func add(info:Any, fileName:String = #file, methodName:String = #function) {
        if isEnabled {
            print("› [SampleApp]: [\(fileName.components(separatedBy: "/").last!.components(separatedBy: ".").first!).\(methodName)] : \(info)")
        }
    }
}
