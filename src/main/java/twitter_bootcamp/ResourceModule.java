package twitter_bootcamp;

import dagger.Module;
import dagger.Provides;
import twitter_bootcamp.resources.TwitterResources;
import twitter_bootcamp.services.Twitter4JService;

@Module
public class ResourceModule {
    public ResourceModule() {}

    @Provides
    TwitterResources provideTwitterResources(Twitter4JService twitter4JService) {
        return new TwitterResources(twitter4JService);
    }
}
