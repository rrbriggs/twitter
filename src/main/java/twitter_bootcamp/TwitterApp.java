package twitter_bootcamp;


import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter_bootcamp.config.AppConfiguration;


public class TwitterApp extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    public static void main(final String[] args) throws Exception {
        if (args.length != 2) {
            LOGGER.error("Incorrect number of arguments. The first argument must be 'server'. The second argument" +
                    " needs to be the name of the config yaml file, eg: 'app_config.yml'");
        }
        else {
            new TwitterApp().run(args);
        }
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment) {
        final FilterRegistration.Dynamic CORS = environment.servlets().addFilter("cors", CrossOriginFilter.class);
        CORS.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_ORIGIN_HEADER, "http://localhost:9000");
        CORS.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_METHODS_HEADER, "GET, POST");
        CORS.setInitParameter(CrossOriginFilter.ACCESS_CONTROL_ALLOW_HEADERS_HEADER, "Origin, Content-Type, Method");
        CORS.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), false, "*");

        ServiceComponent component = DaggerServiceComponent.builder()
                .twitterModule(new TwitterModule(configuration))
                .build();

        LOGGER.info("Registering REST resources..");
        environment.jersey().register(component.getTwitterResources());
    }
}