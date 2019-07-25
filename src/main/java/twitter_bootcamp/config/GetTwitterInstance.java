package twitter_bootcamp.config;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetTwitterInstance {
    private Twitter twitter;

    public GetTwitterInstance() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("Config.properties");
        properties.load(inputStream);

        // populate tokens and keys from properties file
        String CONSUMER_KEY = properties.getProperty("CONSUMER_KEY");
        String CONSUMER_SECRET_KEY = properties.getProperty("CONSUMER_SECRET_KEY");
        String ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
        String ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET_KEY)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        inputStream.close();

        this.twitter = twitter;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
