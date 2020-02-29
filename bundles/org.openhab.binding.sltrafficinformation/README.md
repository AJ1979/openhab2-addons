# SLTrafficInformation Binding

This binding uses Stockholms Lokaltrafik's (SL) open API to get information about deviations for configured line numbers and real time information of upcoming traffic for a particular configured station.


## Supported Things

This binding supports the following thing types:

- Deviations
- Realtime Information

You must create an own API key for each thing type by creating an account on:

https://www.trafiklab.se/

and creating a project that shall use the APIs:

- SL Störningsinformation 2
- SL Realtidsinformation 4

Then create your own API codes for those two APIs.

To get correct siteid for a station, use this URL:

https://www.trafiklab.se/api/sl-platsuppslag/konsol

## Discovery

NA


## Thing Configuration

You can configure the things preferably via things files or via the PapaerUI.

### Deviations

#### Configuration Options

*   `apiKeyDeviation` - Your API key from SL for deviations.

*   `lineNumbers` - Line numbers of interest (comma separated list).

*   `refresh` - Specifies the refresh interval in seconds (defalt 600)

### Realtime Information

Journey direction can be either 1 or 2 and is used to filter all traffic in one direction. 
Since different site IDs can have different meaning for 1 and 2, you have to try out which direction to configure for your site ID.

If direction is not configured, destinations will be used for filtering traffic.
**NOTE**: This must be the end destination for the traffic, not a destination on route.

If neither journeyDirection nor destinations is configured, no traffic will be presented.

Line numbers can be used to filter on one or many lines.

Offset can be used to filter out departures that are too close in time to be able to catch.



#### Configuration Options 

*   `apiKeyRealTime` - Your API key from SL for real time information.

*   `siteId` - SL site ID of interest. See link above for getting correct site ID!

*   `timeWindow` - Time window in minutes (0-60).

*   `journeyDirection` - Journey direction (1 or 2).

*   `destinations` - End destination(s) of interest (comma separated list).

*   `lineNUmbers` - Line number(s) of interest (comma separated list).

*   `offset` - Specifies the offset time in seconds (default 0). Used e.g. for setting a walk time to the station.

*   `refresh` - Specifies the refresh interval in seconds (default 600).


## Channels

([deviations]) supports the following channel:

| Channel Type ID     | Item Type | Description                       |
|------------|--------|-----------------------------------------------|
| deviations | String | Found deviations for configured  line numbers.|

([realTimeInformation]) supports the following channel:

| Channel Type ID     | Item Type | Description                                                          |
|------------|--------|----------------------------------------------------------------------------------|
| realTimeInformation | String | Found realtime information for configured site ID and filters.          |
| nextDeparture       | String | Next departure after offset for configured site ID and filters.         |
| secondDeparture     | String | Second departure after offset for configured site ID and filters.       |
| thirdDeparture      | String | Third departure after offset for configured site ID and filters.        |


## Full Example


```
sltrafficinformation:realTimeInformation:balingsnas "Balingsnäs" [ apiKeyRealTime="569ce4711f444b73dfg32ffefe7f0007", siteId="7030", timeWindow="45", destinations="Högdalen, Skärholmen" refresh=3600 ]
sltrafficinformation:realTimeInformation:blacksvampsvagen "Bläcksvampsvägen" [ apiKeyRealTime="569ce4711f444b73dfg32ffefe7f0007", siteId="7021", timeWindow="45", destinations="Fruängen, Skärholmen" refresh=1800 ]
sltrafficinformation:deviations:minaavvikelser "Avvikelser" [ apiKeyDeviation="d5gffb2cc7834bd2b242d936cfd45afg", lineNumbers="703,710,744,40,41,41X" ]

```

