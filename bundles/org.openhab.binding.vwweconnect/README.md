# VW Car Net Binding

This is an OpenHAB binding for VW We Connect Portal.

This binding uses the rest API behind the VW We Connect Portal: 
https://www.portal.volkswagen-we.com


## Supported Things

This binding supports the following thing types:

- Bridge - VW We Connect Portal API 
- Vehicle - Any VW vehicle reachable via the VW We Connect Portal.


## Binding Configuration

You will have to configure the bridge with username and password, these must be the same credentials as used when logging into: 
https://www.portal.volkswagen-we.com. 

You must also configure your pin to be able to lock/unlock and use the start/stop the heater. 

## Discovery

After the configuration of the VW Car Net Bridge all of the available vehicles will be discovered and placed as things in the inbox.

## Thing Configuration

Only the bridge require manual configuration. Vehicles can be added by hand, or you can let the discovery mechanism automatically find all of your vehicles.

## Supported Things and Channels 

### VW We Connect Bridge 

#### Configuration Options

*   username - The username used to connect to http://mypage.verisure.com

*   password - The password used to connect to http://mypage.verisure.com

*   refresh - Specifies the refresh interval in seconds

*   pin - The username's pin code to lock/unlock doors and start/stop the heater


#### Channels

([vwweconnectapi]) supports the following channel:

| Channel Type ID | Item Type | Description                                                                                     |
|-----------------|-----------|-------------------------------------------------------------------------------------------------|
| status          | String    | This channel can be used to trigger an instant refresh by sending a RefreshType.REFRESH command.|


### VW Vehicle

#### Configuration Options

*   VIN - Vehicle Identification Number
    

#### Channels Groups and channels

([vehicle]) supports the following channel groups and channels:


| Channel Group ID#Channel Type ID | Item Type | Description                                                                           |
|-----------------|-----------|-------------------------------------------------------------------------------------------|
| changedByUser   | String    | This channel reports the user that last changed the state of the alarm.           |
| changedVia      | String    | This channel reports the method used to change the status.                       |
| timestamp       | DateTime  | This channel reports the last time the alarm status was changed.                  |
| installationName| String    | This channel reports the installation name.                                                |
| installationId  | Number    | This channel reports the installation ID.                                             |
| alarmStatus     | String    | This channel is used to arm/disarm the alarm. Available alarm status are "DISARMED", "ARMED_HOME" and "ARMED_AWAY".|               |



## Example

### Things-file

````
// Bridge configuration
Bridge verisure:bridge:myverisure "Verisure Bridge" [username="x@y.com", password="1234", refresh="600", pin="111111"] {

     Thing alarm         JannesAlarm         "Verisure Alarm"                  [ deviceId="alarm123456789" ]
     Thing smartLock     JannesSmartLock     "Verisure Entrance Yale Doorman"  [ deviceId="3C446NPO" ]
     Thing smartPlug     JannesSmartPlug     "Verisure SmartPlug"              [ deviceId="3D7GMANV" ]
     Thing waterDetector JannesWaterDetector "Verisure Water Detector"         [ deviceId="3WETQRH5" ] 
     Thing userPresence  JannesUserPresence  "Verisure User Presence"          [ deviceId="uptestgmailcom123456789" ]
}
````

### Items-file

````
// SmartLock and Alarm
Switch   SmartLock                     "Verisure SmartLock"  <lock>   [ "Switchable" ]  {channel="verisure:smartLock:myverisure:JannesSmartLock:smartLockStatus"}
Switch   AutoLock                      "AutoLock"            <lock>   [ "Switchable" ]  {channel="verisure:smartLock:myverisure:JannesSmartLock:autoRelock"}
String   SmartLockVolume               "SmartLock Volume"     <lock>                    {channel="verisure:smartLock:myverisure:JannesSmartLock:smartLockVolume"}
DateTime SmartLockLastUpdated          "SmartLock Last Updated [%1$tY-%1$tm-%1$td %1$tR]" {channel="verisure:smartLock:myverisure:JannesSmartLock:timestamp"}
String   AlarmHome                     "Alarm Home"          <alarm>                    {channel="verisure:alarm:myverisure:JannesAlarm:alarmStatus"}
DateTime  AlarmLastUpdated             "Verisure Alarm Last Updated [%1$tY-%1$tm.%1$td %1$tR]"               {channel="verisure:alarm:myverisure:JannesAlarm:timestamp"}
String   AlarmChangedByUser            "Verisure Alarm Changed By User"                 {channel="verisure:alarm:myverisure:JannesAlarm:changedByUser"}


// SmartPlugs         
Switch   SmartPlugLamp                 "SmartPlug"               <lock>   [ "Switchable" ]  {channel="verisure:smartPlug:myverisure:4ED5ZXYC:smartPlugStatus"}
Switch   SmartPlugGlavaRouter          "SmartPlug Glava Router"  <lock>   [ "Switchable" ]  {channel="verisure:smartPlug:myverisure:JannesSmartPlug:smartPlugStatus"}

// DoorWindow
String DoorWindowLocation              "Door Window Location"    {channel="verisure:doorWindowSensor:myverisure:1SG5GHGT:location"}
String DoorWindowStatus                "Door Window Status"      {channel="verisure:doorWindowSensor:myverisure:1SG5GHGT:state"}

// UserPresence
String UserName                        "User Name"               {channel="verisure:userPresence:myverisure:JannesUserPresence:userName"}
String UserLocationEmail               "User Location Email"     {channel="verisure:userPresence:myverisure:JannesUserPresence:webAccount"}
String UserLocationName                "User Location Name"      {channel="verisure:userPresence:myverisure:JannesUserPresence:userLocationStatus"}

String UserNameGlava                   "User Name Glava"               {channel="verisure:userPresence:myverisure:userpresencetestgmailcom123456789:userName"}
String UserLocationEmailGlava          "User Location Email Glava"     {channel="verisure:userPresence:myverisure:userpresencetestgmailcom123456789:webAccount"}
String UserLocationNameGlava           "User Location Name Glava"      {channel="verisure:userPresence:myverisure:userpresencetestgmailcom1123456789:userLocationStatus"}

// Broadband Connection
String CurrentBBStatus                 "Broadband Connection Status"       {channel="verisure:broadbandConnection:myverisure:bc123456789:status"}

````

### Sitemap

````
    Frame label="SmartLock and Alarm" {
        Text label="SmartLock and Alarm" icon="groundfloor" {
            Frame label="Yale Doorman SmartLock" {
                Switch item=SmartLock label="Yale Doorman SmartLock" icon="lock.png"
            }
            Frame label="Verisure Alarm" {
                Switch  item=AlarmHome  icon="alarm" label="Verisure Alarm"  mappings=["DISARMED"="Disarm", "ARMED_HOME"="Arm Home", "ARMED_AWAY"="Arm Away"]
            }
            Frame label="Yale Doorman SmartLock AutoLock" {
                Switch item=AutoLock label="Yale Doorman SmartLock AutoLock" icon="lock.png"
            }
            Frame label="Yale Doorman SmartLock Volume"  {
                Switch  item=SmartLockVolume  icon="lock" label="Yale Doorman SmartLock Volume"  mappings=["SILENCE"="Silence", "LOW"="Low", "HIGH"="High"]
            }
            Text item=AlarmHomeInstallationName label="Alarm Installation [%s]"
            Text item=AlarmChangedByUser label="Changed by user [%s]"
            Text item=AlarmLastUpdated
            Text item=SmartLockStatus label="SmartLock status [%s]"
            Text item=SmartLockLastUpdated
            Text item=SmartLockOperatedBy label="Changed by user [%s]"
            Text item=DoorWindowStatus label="Door State"
            Text item=DoorWindowLocation
        }
    }

    Frame label="SmartPlugs" {
        Text label="SmartPlugs" icon="attic" {
            Frame label="SmartPlug Lamp" {
                Switch item=SmartPlugLamp label="Verisure SmartPlug Lamp" icon="smartheater.png"
            }
        }
    }	
    
    Frame label="User Presence" {
		Text label="User Presence" icon="attic" {
			Frame label="User Presence Champinjonvägen" {
				Text item=UserName label="User Name [%s]"
				Text item=UserLocationEmail label="User Email [%s]"
                     Text item=UserLocationStatus label="User Location Status [%s]"
			}
		}
	}

	Frame label="Broadband Connection" {
		Text label="Broadband Connection" icon="attic" {
			Frame label="Broadband Connection Champinjonvägen" {
				Text item=CurrentBBStatus label="Broadband Connection Status [%s]"
			}
		}
	}
    
````
