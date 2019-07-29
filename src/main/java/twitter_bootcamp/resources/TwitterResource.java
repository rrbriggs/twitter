package twitter_bootcamp.resources;


import org.hibernate.validator.constraints.NotEmpty;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterResource {

    private final int TWEET_LENGTH = 280;

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    private Twitter twitter;

    public TwitterResource(Twitter twitter) {
        this.twitter = twitter;
    }

    @GET
    @Path("/timeline")
    public Response getTimeline() {
        try {
            LOGGER.info("Getting Timeline.. ");

            return Response
                    .status(Response.Status.OK)
                    .entity(twitter.getHomeTimeline())
                    .build();
        }
        catch (TwitterException e) {
            LOGGER.warn("Twitter Exception thrown while attempting to getTimeline()", e);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error getting your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    @POST
    @Path("/tweet")
    public Response postTweet(@FormParam("message") @NotEmpty String message) {

        if (message.length() <= TWEET_LENGTH) {
            try {
                Status status = twitter.updateStatus(message);

                LOGGER.info("User: {} is tweeting: {}", status.getUser().getName(),status.getText());

                return Response
                        .status(Response.Status.CREATED)
                        .entity(status)
                        .build();
            }
            catch (TwitterException e) {
                LOGGER.warn("Twitter Exception thrown while attempting to postTweet() with message of: {}", message, e);

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("The problem here is with us, not you. If the problem persists, " +
                                "submit an issue (https://github.com/rrbriggs/BrigBot/issues)")
                        .build();
            }
        }
        else {
            LOGGER.warn("Attempted to post tweet that exceeds TWEET_LENGTH: {}", TWEET_LENGTH);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Tweet was too long. Limit tweet to " + TWEET_LENGTH + " characters.")
                    .build();
        }
    }

    public int getTweetLength() {
        return TWEET_LENGTH;
    }
}