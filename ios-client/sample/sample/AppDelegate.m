//
//  AppDelegate.m
//  sample
//
//  Created by Gal Dolber on 1/24/14.
//  Copyright (c) 2014 clojure-objc. All rights reserved.
//

#import "AppDelegate.h"
#import "clojure/lang/RT.h"
#import "clojure/lang/Var.h"
#import "clojure/lang/ObjC.h"
#import "ReplClient.h"
#import "harlanji/clojureseed/ios/core_main.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    [ClojureLangObjC setObjC];
    [ClojureLangRT load__WithNSString:@"harlanji/clojureseed/ios/core"];

    // to start repl: uncomment, start jvm repl and call (remote-repl)
    // [ReplClient connect:@"localhost"];
    // return YES;
    
    // to run app
    [HarlanjiClojureseedIoscore_main_get_VAR_() invoke];
    return YES;
}

@end
