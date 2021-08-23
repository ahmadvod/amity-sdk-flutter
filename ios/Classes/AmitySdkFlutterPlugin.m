#import "AmitySdkFlutterPlugin.h"
#if __has_include(<amity_sdk_flutter/amity_sdk_flutter-Swift.h>)
#import <amity_sdk_flutter/amity_sdk_flutter-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "amity_sdk_flutter-Swift.h"
#endif

@implementation AmitySdkFlutterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftAmitySdkFlutterPlugin registerWithRegistrar:registrar];
}
@end
