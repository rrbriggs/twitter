package main;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;

@Path("/api/1.0/twitter")
public class TwitterRESTController {

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
    public String postTweet(String message) {
        System.out.println("message being posted: " + message);

        try {
            GetTwitterInstance twitterInstance = new GetTwitterInstance();
            PostTweet postTweet = new PostTweet(twitterInstance.getTwitter(), message);
            return "you posted: " + message;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}