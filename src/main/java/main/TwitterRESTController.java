package main;


import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("/api/1.0/twitter")
public class TwitterRESTController {
    private final int TWEET_LENGTH = 280;

    @GET
    @Path("/timeline")
    public String getTimeline() throws IOException {
        GetTwitterInstance twitterInstance = new GetTwitterInstance();
        FetchTimeline fetchTimeline = new FetchTimeline(twitterInstance.getTwitter());

        String tweetString = "";
        for (String tweet: fetchTimeline.getTimeline()) {
            tweetString += tweet + "\n";
        }

        return tweetString;
    }

    @POST
    @Path("/tweet/message")
    public Response postTweet(String message) {
        System.out.println("message being posted: " + message);

        if (message.length() <= TWEET_LENGTH) {
            try {
                GetTwitterInstance twitterInstance = new GetTwitterInstance();
                PostTweet postTweet = new PostTweet(twitterInstance.getTwitter(), message);
                if (postTweet.getError() == null) {
                    return Response
                            .status(Response.Status.OK)
                            .entity(message)
                            .build();
                }
                else {
                    return Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(postTweet.getError().getErrorMessage())
                            .build();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
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