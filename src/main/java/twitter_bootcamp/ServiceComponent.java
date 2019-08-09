package twitter_bootcamp;

import dagger.Component;
import javax.inject.Singleton;
import twitter_bootcamp.resources.TwitterResources;

@Singleton
@Component(modules = {TwitterModule.class})
public interface ServiceComponent {
    TwitterResources getTwitterResources();
}
