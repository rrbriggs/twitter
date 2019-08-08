package twitter_bootcamp;

import dagger.Component;
import javax.inject.Singleton;
import twitter_bootcamp.resources.TwitterResources;
import twitter_bootcamp.services.Twitter4JService;

@Singleton
@Component(modules = {TwitterModule.class, ResourceModule.class})
public interface ServiceComponent {
    Twitter4JService getTwitter4JService();

    TwitterResources getTwitterResources();
}
