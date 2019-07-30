package twitter_bootcamp.resources;


import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.ResponseList;
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
import twitter_bootcamp.services.Twitter4JService;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    private Twitter4JService twitter4JService;

    public TwitterResource(Twitter4JService twitter4JService) {
        this.twitter4JService = twitter4JService;
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {

        LOGGER.info("GET request to get twitter timeline. ");

        ResponseList<Status> twitterResponse = twitter4JService.getTwitterTimeline();

        if (twitterResponse != null) {
            LOGGER.info("Timeline received successfully.");

            return Response
                    .status(Response.Status.OK)
                    .entity(twitterResponse)
                    .build();
        }
        else {
            LOGGER.error("Error while trying to get timeline from Twitter4JService, returned null value.");

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

        Status status = twitter4JService.sendTweet(message);

        if (status != null) {
            LOGGER.info("Tweet successfully sent. ");
            return Response
                    .status(Response.Status.CREATED)
                    .entity(status)
                    .build();
        } else {
            LOGGER.error("Status returned from Twitter4JService is null: ");

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an issue posting your tweet. Ensure the tweet length does not exceed "
                            + twitter4JService + "characters.")
                    .build();
        }
    }
}