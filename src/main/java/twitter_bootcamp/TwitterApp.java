package twitter_bootcamp;


import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.config.TwitterAuth;
import twitter_bootcamp.resources.TwitterResource;


public class TwitterApp extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    public static void main(final String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment) {
        LOGGER.info("Registering REST resources..");

        TwitterAuth twitterAuth = configuration.getTwitterAuth();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterAuth.getConsumerKey())
                .setOAuthConsumerSecret(twitterAuth.getConsumerSecretKey())
                .setOAuthAccessToken(twitterAuth.getAccessToken())
                .setOAuthAccessTokenSecret(twitterAuth.getAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        environment.jersey().register(new TwitterResource(twitter));
    }
}