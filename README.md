# BrigBot

Welcome to the BrigBot Twitter interface. 

This application has the basic functionality of:
1: Sending a tweet
2: Viewing your Timeline

## Building and running with Gradle:

Clone the repo with `git clone --single-branch --branch dropwizard https://github.com/rrbriggs/BrigBot.git`

*This application requires a Config.properties file that contains your Twitter tokens and keys.
There an example file called (example_Config.properties) that lives in the examples folder of this project.
Once you have populated this file with your information, make sure to rename the file to `Config.properties`.
Note: for this file, you must add your key or token after the "=" for example:*

`CONSUMER_KEY=YourConsumerKey`

Open the directory in terminal or navigate to it, then to build the app enter:

`./gradlew build`

To run the app, then enter:
`./gradlew run`

### To send a Tweet:
POST plain text / string to the endpoint /api/1.0/twitter/tweet/message
For example, from terminal: `curl -d "message=Hello World" -X POST http://localhost:8080/api/1.0/twitter/tweet`

### To get your Timeline:
GET from the endpoint /api/1.0/twitter/timeline
For example, from terminal: `curl  http://localhost:8080/api/1.0/twitter/timeline;`

## Running unit tests:
In terminal, at the project root directory run:
`gradle clean test`



## Note:

Requires the [Twitter4j library](http://twitter4j.org/en/http://twitter4j.org/en/)
