package main;

import twitter4j.Twitter;

import javax.ws.rs.*;

@Path("/api/1.0/twitter/tweet")
//@Produces(MediaType.APPLICATION_JSON)
public class TwitterRESTController {

    @POST
    @Path("/message")
    public String postTweet(String message) {
        System.out.println("message being posted: " + message);

        try {
            GetTwitterInstance getTwitterInstance = new GetTwitterInstance();
            Twitter twitter = (Twitter) getTwitterInstance;
            PostTweet postTweet = new PostTweet(twitter, message);
            return "you posted" + message;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
