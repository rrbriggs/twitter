package main;

import javax.ws.rs.*;

@Path("/api/1.0/twitter/tweet")
//@Produces(MediaType.APPLICATION_JSON)
public class TwitterRESTController {

    @POST
    @Path("/message")
    public String postTweet(String message) {
        System.out.println("message being posted: " + message);
        return "you posted" + message;
    }
}
