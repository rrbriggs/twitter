package twitter_bootcamp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter_bootcamp.TwitterApp;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.config.TwitterAuth;

public final class Twitter4JService {

    private static final Twitter4JService INSTANCE = new Twitter4JService();

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    private AppConfiguration configuration;

    private Twitter twitter;

    private Twitter4JService() {}

    public ResponseList<Status> getTwitterTimeline() throws TwitterException {
        return twitter.getHomeTimeline();
    }

    public Status sendTweet(String message) throws TwitterException {
        return twitter.updateStatus(message);
    }

    public Twitter getTwitter() {
        TwitterAuth twitterAuth = configuration.getTwitterAuth();

        LOGGER.info("Setting twitter OAuth config: ");
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterAuth.getConsumerKey())
                .setOAuthConsumerSecret(twitterAuth.getConsumerSecretKey())
                .setOAuthAccessToken(twitterAuth.getAccessToken())
                .setOAuthAccessTokenSecret(twitterAuth.getAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();

        return twitter;
    }

    public void setTwitter(Twitter twitter) { this.twitter = twitter; }

    public AppConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(AppConfiguration configuration) {
        this.configuration = configuration;

        twitter = getTwitter();
    }

    public static Twitter4JService getInstance() {
        return INSTANCE;
    }
}
