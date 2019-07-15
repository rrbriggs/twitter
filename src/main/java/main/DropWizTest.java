package main;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DropWizTest extends Application<AppConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DropWizTest.class);

    public static void main(final String[] args) throws Exception {
        new DropWizTest().run(args);
    }

    @Override
    public void run(final AppConfiguration configuration, final Environment environment)
            throws Exception {
        // TODO: cleanup some of this
        LOGGER.info("Starting application with name: {}", configuration.getAppName());
        LOGGER.info("Registering REST resources..");

        //environment.jersey().register();

        //final JsonFileRulesRepository rulesRepository = new JsonFileRulesRepository();
        //final RulesResource rulesResource = new RulesResource(rulesRepository);

        //final TwitterRESTController twitterRESTController = new TwitterRESTController();

        environment.jersey().register(new TwitterRESTController());
    }
}