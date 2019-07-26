package twitter_bootcamp;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Twitter;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.config.GetTwitterInstance;
import twitter_bootcamp.resources.TwitterResource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TwitterApp extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    public static void main(final String[] args) throws Exception {
        new TwitterApp().run(args);
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment) throws IOException {
        LOGGER.info("Starting application with name: {}", configuration.getAppName());
        LOGGER.info("Registering REST resources..");

        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("Config.properties");
        properties.load(inputStream);
        inputStream.close();


        GetTwitterInstance twitterInstance = new GetTwitterInstance(properties);
        Twitter twitter = twitterInstance.getTwitter();

        environment.jersey().register(new TwitterResource(twitter));
    }
}