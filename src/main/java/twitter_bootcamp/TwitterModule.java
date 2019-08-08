package twitter_bootcamp;


import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.config.TwitterAuth;
import twitter_bootcamp.services.Twitter4JService;


@Module
public class TwitterModule {
    AppConfiguration configuration;

    public TwitterModule(AppConfiguration configuration){
        this.configuration = configuration;
    }

    @Provides
    Twitter4JService provideTwitter4JService(Twitter twitter, AppConfiguration configuration) {
        return new Twitter4JService(twitter, configuration);
    }

    @Provides
    @Singleton
    Twitter provideTwitter() {
        TwitterAuth twitterAuth = configuration.getTwitterAuth();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(twitterAuth.getConsumerKey())
                .setOAuthConsumerSecret(twitterAuth.getConsumerSecretKey())
                .setOAuthAccessToken(twitterAuth.getAccessToken())
                .setOAuthAccessTokenSecret(twitterAuth.getAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());

        return tf.getInstance();
    }
}