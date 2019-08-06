package twitter_bootcamp.resources;


import org.hibernate.validator.constraints.NotEmpty;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter_bootcamp.TwitterApp;
import twitter_bootcamp.services.Twitter4JService;
import twitter_bootcamp.services.Twitter4JServiceException;


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
            LOGGER.info("Timeline received successfully.");

            return twitter4JService.getTwitterTimeline()
                    .map(socialPost -> Response.ok(socialPost)
                    .build())
                    .get();

        }
        catch (Exception e) {
            LOGGER.error("Twitter Exception thrown while attempting to getTimeline()", e);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error getting your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    @GET
    @Path("/timeline/filter")
    public Response getFilteredTimeline(@QueryParam("filterKey") @NotEmpty String filterKey) {

        // filter latest tweets by a keyword passed in
        try {
            LOGGER.info("Attempting to filterTimeline with filterKey of: {}", filterKey);

            return twitter4JService.filterTimeline(filterKey)
                    .map(filteredPost -> Response.ok(filteredPost)
                    .build())
                    .get();
        }
        catch (Twitter4JServiceException e) {
            LOGGER.info("No twitter posts match the filter key of: {}", filterKey);

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity("No Twitter posts match your filter key.")
                    .build();
        }
        catch (Exception e) {
            LOGGER.error("Twitter Exception thrown while attempting to filterTimeline() with filterKey of '{}",
                    filterKey, e);

            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error filtering your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    @POST
    @Path("/tweet")
    public Response postTweet(@FormParam("message") @NotEmpty String message) {
        LOGGER.info("POST request to send a tweet received. ");

        try {
            LOGGER.info("Tweet successfully sent. ");
            return twitter4JService.sendTweet(message)
                    .map(sentTweet -> Response.ok(sentTweet)
                    .build())
                    .get();

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