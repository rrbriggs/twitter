package twitter_bootcamp.resources;


import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.Status;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter_bootcamp.TwitterApp;
import twitter_bootcamp.models.TwitterUser;
import twitter_bootcamp.services.Twitter4JService;
import twitter_bootcamp.services.Twitter4JServiceException;

import java.util.List;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterResources {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    private Twitter4JService twitter4JService;

    public TwitterResources(Twitter4JService twitter4JService) {
        this.twitter4JService = twitter4JService;
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {

        LOGGER.info("GET request to get twitter timeline. ");

        try {
            List<TwitterUser> userList = twitter4JService.getTwitterTimeline();

            LOGGER.info("Timeline received successfully.");

            return Response
                    .status(Response.Status.OK)
                    .entity(userList)
                    .build();
        }
        catch (Exception e) {
            LOGGER.error("Twitter Exception thrown while attempting to getTimeline()", e);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error getting your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    @POST
    @Path("/tweet")
    public Response postTweet(@FormParam("message") @NotEmpty String message) {
        LOGGER.info("POST request to send a tweet received. ");

        try {
            Status status = twitter4JService.sendTweet(message);

            LOGGER.info("Tweet successfully sent. ");
            return Response
                    .status(Response.Status.CREATED)
                    .entity(status)
                    .build();

        }
        catch (Twitter4JServiceException e) {
            LOGGER.info("Error posting tweet: ", e);

            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Tweet exceeded maximum allowed characters. Ensure the tweet length does not exceed "
                            + twitter4JService + "characters.")
                    .build();

        }
        catch (Exception e) {
            LOGGER.warn("Error posting tweet: ", e);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an issue posting your tweet. Please try again in a few minutes.")
                    .build();
        }
    }
}