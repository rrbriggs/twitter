package twitter_bootcamp;


import dagger.Module;
import dagger.Provides;
import twitter4j.Twitter;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.services.Twitter4JService;

import javax.inject.Singleton;

@Module
public class Twitter4JServiceModule {

    @Provides
    Twitter4JService provideTwitter4JService(Twitter twitter, AppConfiguration configuration){
        return new Twitter4JService(twitter, configuration);
    }
}
