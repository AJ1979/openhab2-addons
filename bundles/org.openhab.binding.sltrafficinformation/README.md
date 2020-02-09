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

#### Configuration Options 

*   `apiKeyRealTime` - Your API key from SL for real time information.

*   `siteId` - SL site ID of interest.

*   `timeWindow` - Time window in minutes (0-60).

*   `destinations` - End destination(s) for line (comma separated list).

*   `refresh` - Specifies the refresh interval in seconds (default 600).


## Channels

([deviations]) supports the following channel:

| Channel Type ID     | Item Type | Description                       |
|------------|--------|-----------------------------------------------|
| deviations | String | Found deviations for configured  line numbers.|

([realTimeInformation]) supports the following channel:

| Channel Type ID     | Item Type | Description                                     |
|------------|--------|-------------------------------------------------------------|
| realTimeInformation | String | Found realtime information for configured  site ID.|


## Full Example


```
sltrafficinformation:realTimeInformation:balingsnas "Balingsnäs" [ apiKeyRealTime="569ce4711f444b73dfg32ffefe7f0007", siteId="7030", timeWindow="45", destinations="Högdalen, Skärholmen" refresh=3600 ]
sltrafficinformation:realTimeInformation:blacksvampsvagen "Bläcksvampsvägen" [ apiKeyRealTime="569ce4711f444b73dfg32ffefe7f0007", siteId="7021", timeWindow="45", destinations="Fruängen, Skärholmen" refresh=1800 ]
sltrafficinformation:deviations:minaavvikelser "Avvikelser" [ apiKeyDeviation="d5gffb2cc7834bd2b242d936cfd45afg", lineNumbers="703,710,744,40,41,41X" ]

```

