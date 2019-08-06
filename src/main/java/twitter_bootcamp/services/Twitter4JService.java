package twitter_bootcamp.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.TwitterFactory;
import twitter_bootcamp.TwitterApp;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.config.TwitterAuth;
import twitter_bootcamp.models.SocialPost;
import twitter_bootcamp.models.SocialUser;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public final class Twitter4JService {

    private static final Twitter4JService INSTANCE = new Twitter4JService();

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    protected static final int MAX_TWEET_LENGTH = 280;

    private AppConfiguration configuration;

    private Twitter twitter;

    private Twitter4JService() {}

    // for testing purposes
    public Twitter4JService(Twitter twitter, AppConfiguration configuration) {
        this.twitter = twitter;
        this.configuration = configuration;
    }

    public Optional<List<SocialPost>> getTwitterTimeline() throws Twitter4JServiceException {
        LOGGER.info("Getting Timeline.. ");
        try {
            List<SocialPost> streamSocialPostList = twitter.getHomeTimeline()
                    .stream()
                    .map(this::socialPostBuilder)
                    .collect(toList());

            return Optional.of(streamSocialPostList);
        }
        catch (TwitterException e) {
            LOGGER.error("Error getting twitter timeline. ", e);
            throw new Twitter4JServiceException();
        }
    }

    private SocialPost socialPostBuilder(Status status) {
        SocialPost socialPost = new SocialPost();
        SocialUser socialUser = new SocialUser();

        socialUser.setName(status.getUser().getName());
        socialUser.setTwitterHandle(status.getUser().getScreenName());
        socialUser.setProfileImageUrl(status.getUser().getProfileImageURL());

        socialPost.setSocialUser(socialUser);
        socialPost.setCreatedAt(status.getCreatedAt());
        socialPost.setMessage(status.getText());


        return socialPost;
    }

    public Optional<List<SocialPost>> filterTimeline(String filterKey) throws Twitter4JServiceException, TwitterException {
        LOGGER.info("Filtering from Timeline using filterKey of {}", filterKey);

        List<SocialPost> timelineFiltered = twitter.getHomeTimeline()
                .stream()
                .filter(status -> containsIgnoreCase(status.getText(), filterKey))
                .map(this::socialPostBuilder)
                .collect(toList());

        if (timelineFiltered.isEmpty()) {
            throw new Twitter4JServiceException("No filtered objects found");
        }
        else {
            LOGGER.info("Successfully filtered");

            return Optional.of(timelineFiltered);
        }
    }

    public Optional<SocialPost> sendTweet(String message) throws Twitter4JServiceException, RuntimeException {

        // throw exception if tweet message is too long
        if (message.length() > MAX_TWEET_LENGTH) {
            LOGGER.warn("User tweet message exceeded tweet max length. User tweet: {}", message);
            throw new Twitter4JServiceException("Maximum tweet length exceeded.");
        }
        else {
            try {
                //Status status = twitter.updateStatus(message);
                //LOGGER.info("User: {} is tweeting: {}", status.getUser().getName(),status.getText());

                //return status;

                //Optional<Status> status;

                return Optional.of(twitter.updateStatus(message)).map(this::socialPostBuilder);
            }
            catch (TwitterException e) {
                LOGGER.error("Unexpected error when calling twitter.updateStatus with the message of: {}", message, e);
                throw new RuntimeException();
            }
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


    public static Twitter4JService getInstance() {
        return INSTANCE;
    }
}
