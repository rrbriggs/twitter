package twitter_bootcamp;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TwitterApp extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    public static void main(final String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment) throws IOException {
        LOGGER.info("Starting application with name: {}", configuration.getAppName());
        LOGGER.info("Registering REST resources..");

        environment.jersey().register(new TwitterResource());
    }
}