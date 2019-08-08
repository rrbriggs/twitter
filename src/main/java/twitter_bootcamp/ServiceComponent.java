package twitter_bootcamp;

import dagger.Component;
import javax.inject.Singleton;
import twitter_bootcamp.services.Twitter4JService;

@Singleton
@Component(modules = TwitterModule.class)
public interface ServiceComponent {
    Twitter4JService getTwitter4JService();
}
