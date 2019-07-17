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
For example, from terminal: `curl -d "Hello World" -X POST http://localhost:8080/api/1.0/twitter/tweet/message`

### To get your Timeline:
GET from the endpoint /api/1.0/twitter/timeline
For example, from terminal: `curl  http://localhost:8080/api/1.0/twitter/timeline;`


## Building and running BrigBot.jar from terminal:

Ensure you have all of the main .java files (AppLogic, FetchTimeline, Main, PostTweet) in the same directory,
as well as the twitter4j dependency jar file (twitter4j-core-4.0.7.jar) which can be found in the libs folder.
In this directory, ensure you have no .class files other than those specified here.

To compile enter the command:
`javac -cp twitter4j-core-4.0.7.jar: Main.java`

You should now have compiled your .java files into .class files.

Test that this worked by running:
`java -cp twitter4j-core-4.0.7.jar: Main`

Create a manifest.txt file in the same directory. You may use the one provided in the examples folder of this project.
You may need to update the twitter4j jar to reflect your version.

To create a single .jar run:
`jar cvfm BrigBot.jar manifest.txt *class twitter4j-core-4.0.7.jar`

This application requires a Config.properties file that contains your Twitter tokens and keys.
There an example file called (example_Config.properties) that lives in the examples folder of this project,
for this file, you must add your key or token after the "=" for example:

`CONSUMER_KEY=YourConsumerKey`

remove "example_" from the file, so that it now reads "Config.properties" and make sure that it is in the same directory
as BrigBot.jar

To Run, enter:
`java -jar BrigBot.jar`

## Note:

Requires the [Twitter4j library](http://twitter4j.org/en/http://twitter4j.org/en/)
