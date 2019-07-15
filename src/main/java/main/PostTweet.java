package main;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class PostTweet {
    public PostTweet(Twitter twitter, String tweet) {
        try {
            Status status = twitter.updateStatus(tweet);

            if (status != null) {
                System.out.println("The following message was tweeted:");

                // status.getText() is the tweet just posted
                System.out.println(status.getText() + "\n");
            }
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}