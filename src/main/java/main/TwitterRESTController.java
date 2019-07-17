package main;


import twitter4j.Status;
import twitter4j.TwitterException;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api/1.0/twitter")
@Produces(MediaType.APPLICATION_JSON)
public class TwitterRESTController {
    private final int TWEET_LENGTH = 280;

    @GET
    @Path("/timeline")
    public Response getTimeline() {
        try {
            GetTwitterInstance twitterInstance = new GetTwitterInstance();

            return Response
                    .status(Response.Status.OK)
                    .entity(twitterInstance.getTwitter().getHomeTimeline()) //fetchTimeline.getTimeline()
                    .build();
        }
        catch (IOException | TwitterException e) {
            e.printStackTrace();
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("There was an error getting your timeline. Please try again in a few minutes.")
                    .build();
        }
    }

    @POST
    @Path("/tweet/message")
    public Response postTweet(String message) {
        System.out.println("message being posted: " + message);

        if (message.length() <= TWEET_LENGTH) {
            try {
                GetTwitterInstance twitterInstance = new GetTwitterInstance();
                Status status = twitterInstance.getTwitter().updateStatus(message);
                    return Response
                            .status(Response.Status.CREATED)
                            .entity(message)
                            .build();
            }
            catch (Exception e) {
                e.printStackTrace();

                return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(e)
                        .build();
            }
        }
        else {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Tweet was too long. Limit tweet to " + TWEET_LENGTH + " characters.")
                    .build();
        }
    }
}