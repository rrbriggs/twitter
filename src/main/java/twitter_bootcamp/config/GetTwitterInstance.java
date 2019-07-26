package twitter_bootcamp.config;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


import java.util.Properties;

public class GetTwitterInstance {
    private Twitter twitter;

    public GetTwitterInstance(Properties properties) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(properties.getProperty("CONSUMER_KEY"))
                .setOAuthConsumerSecret(properties.getProperty("CONSUMER_SECRET_KEY"))
                .setOAuthAccessToken(properties.getProperty("ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(properties.getProperty("ACCESS_TOKEN_SECRET"));

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        this.twitter = twitter;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
