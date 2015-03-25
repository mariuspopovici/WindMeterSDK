//
//  WindMeterSDK.h
//  WindMeterSDK
//
//  Created by Derek Trauger on 6/26/14.
//  Copyright (c) 2014 WeatherFlow. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <AVFoundation/AVFoundation.h>
#import <AudioUnit/AudioUnit.h>
#import <AudioToolbox/AudioToolbox.h>
#import <MediaPlayer/MediaPlayer.h>
#import <CoreLocation/CoreLocation.h>
#import "AnemometerInterface.h"
#import "AnemometerObservation.h"
#import "SensorStatus.h"
#import "DeviceLocation.h"

#define VOLUME_SETTING_KEY @"wf_sensor_volume_key"

@interface WindMeterSDK : NSObject <CLLocationManagerDelegate> {
    AVAudioPlayer *audioPlayer;
    AVAudioRecorder *audioRecorder;
    BOOL isListening;
    BOOL capturedAudio;
    float currentRPM;
	AnemometerInterface *anemometerRef;
    AnemometerObservation *anemometerOb;
    DeviceLocation *deviceLocation;
    void (^_valueChangedHandler)(AnemometerObservation *value);
    MPMusicPlayerController *audioPlayerController;
    CLLocationManager *locationManager;
    NSTimer *timer1;
}

@property (nonatomic, assign) AnemometerInterface *anemometerRef;
@property (assign) BOOL isListening;


- (id)init;
- (void)startListener;
- (void)stopListener;
- (void)openClosedChangedWithValue:(float)newValue;
- (void) reportValueChange:(void(^)(AnemometerObservation *))handler;
- (void)destroySensor;

@end
