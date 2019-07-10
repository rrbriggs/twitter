# BrigBot

Welcome to the BrigBot Twitter interface. 

This application has the basic functionality of:
1: Sending a tweet
2: Viewing your Timeline

## Building and Running BrigBot.jar from terminal:

With all of the main .java files (AppLogic, FetchTimeline, Main, PostTweet) in the same directory.
In this directory, ensure you have no .class files other than those specified here.

To compile enter the command:
javac -cp twitter4j-core-4.0.7.jar: Main.java

You should now have compiled your .java files into .class files.

Test that this worked by running:
java -cp twitter4j-core-4.0.7.jar: Main

Create a manifest.txt file in the same directory. You may use the one provided in the examples folder of this project.
You may need to update the twitter4j jar to reflect your version.

To create a single .jar run:
jar cvfm BrigBot.jar manifest.txt *class twitter4j-core-4.0.7.jar

This application requires a Config.properties file that contains your Twitter tokens and keys.
There an example file called (example_Config.properties) that lives in the examples folder of this project,
for this file, you must add your key or token after the "=" for example:

CONSUMER_KEY=YourConsumerKey

remove "example_" from the file, so that it now reads "Config.properties" and make sure that it is in the same directory
as BrigBot.jar

To Run, enter:
java -jar BrigBot.jar

## Note:

Requires the Twitter4j library (http://twitter4j.org/en/http://twitter4j.org/en/)
