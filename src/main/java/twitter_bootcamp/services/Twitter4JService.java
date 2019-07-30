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

    private final int MAX_TWEET_LENGTH = 280;

    private AppConfiguration configuration;

    private Twitter twitter;

    private Twitter4JService() {}

    public ResponseList<Status> getTwitterTimeline() {
        try {
            LOGGER.info("Getting Timeline.. ");
            return twitter.getHomeTimeline();
        }
        catch (TwitterException e) {
            LOGGER.error("Twitter Exception thrown while attempting to getTimeline()", e);
            return null;
        }
    }

    public Status sendTweet(String message) {
        try {
            if (message.length() <= MAX_TWEET_LENGTH) {
                Status status = twitter.updateStatus(message);
                LOGGER.info("User: {} is tweeting: {}", status.getUser().getName(),status.getText());

                return status;
            }
            else {
                LOGGER.warn("User tweet message exceeded tweet max length. User tweet: {}", message);
                return null;
            }
        }
        catch (TwitterException e) {
            LOGGER.error("Twitter Exception thrown while attempting to postTweet() with message of: {}", message, e);
            return null;
        }
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

    public int getMaxTweetLength() {
        return MAX_TWEET_LENGTH;
    }


    public static Twitter4JService getInstance() {
        return INSTANCE;
    }
}
