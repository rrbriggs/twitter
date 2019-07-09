# BrigBot

Welcome to the BrigBot Twitter interface. 

This application has the basic functionality of:
1: Sending a tweet
2: Viewing your Timeline

## Running BrigBot.jar:

This application requires a Config.properties file that contains your Twitter tokens and keys.
There an example file called (example_Config.properties) that lives in /out/artifacts/BrigBot_jar
for this file, you may add your key or token after the "=" for example:

CONSUMER_KEY=YourConsumerKey

remove "example_" from the file, so that it now reads "Config.properties" and make sure that it is in the same directory
as BrigBot.jar

To Run, enter:
java -jar BrigBot.jar

## Note:

Requires the Twitter4j library (http://twitter4j.org/en/http://twitter4j.org/en/)
