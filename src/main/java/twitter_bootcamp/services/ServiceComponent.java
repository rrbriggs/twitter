package twitter_bootcamp.services;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ServiceComponent {
    private final Twitter4JService twitter4JService;

    @Inject
    public ServiceComponent(twitter4JService){
        this.twitter4JService = twitter4JService;
    }

}
