#import "PinkRouterPlugin.h"
#if __has_include(<pink_router/pink_router-Swift.h>)
#import <pink_router/pink_router-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "pink_router-Swift.h"
#endif

@implementation PinkRouterPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPinkRouterPlugin registerWithRegistrar:registrar];
}
@end
