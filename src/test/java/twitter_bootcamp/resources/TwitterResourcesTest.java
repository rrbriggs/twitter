package twitter_bootcamp.resources;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.Status;
import twitter4j.User;
import twitter_bootcamp.models.SocialPost;
import twitter_bootcamp.services.Twitter4JService;
import twitter_bootcamp.services.Twitter4JServiceException;

import javax.ws.rs.core.Response;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class TwitterResourcesTest {

    TwitterResources twitterResources;

    @Mock
    Twitter4JService twitter4JService;

    @Mock
    Status mockStatus;

    @Mock
    User user;

    @Mock
    SocialPost socialPost;

    @Mock
    List<SocialPost> socialPostList;

    @Mock
    Twitter4JServiceException twitter4JServiceException;

    @Mock
    RuntimeException runtimeException;

    String tweet = "{\"message\": \"testString\"}";

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        twitterResources = new TwitterResources(twitter4JService);

        socialPostList.add(socialPost);

    }

    @Test
    final void getTimeline_ResponseMatchesStatus() throws Exception {
        when(twitter4JService.getTwitterTimeline()).thenReturn(Optional.of(socialPostList));
        Response response = twitterResources.getTimeline();

        // test response data is what is expected
        assertEquals(socialPostList, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    final void getTimeline_Twitter4JServiceExceptionThrownResponseStatusIsInternalServerError() throws Exception {

        when(twitter4JService.getTwitterTimeline()).thenThrow(twitter4JServiceException);

        Response response = twitterResources.getTimeline();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void getUserTimeline_ResponseMatchesStatus() throws Exception {
        when(twitter4JService.getTwitterUserTimeline()).thenReturn(Optional.of(socialPostList));
        Response response = twitterResources.getUserTimeline();

        // test response data is what is expected
        assertEquals(socialPostList, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    final void getUserTimeline_Twitter4JServiceExceptionThrownResponseStatusIsInternalServerError() throws Exception {

        when(twitter4JService.getTwitterUserTimeline()).thenThrow(twitter4JServiceException);

        Response response = twitterResources.getUserTimeline();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void postTweet_ResponseMatchesStatus() throws Exception {
        String message = "testString";

        when(twitter4JService.sendTweet(socialPost.getMessage())).thenReturn(Optional.of(socialPost));
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        Response response = twitterResources.postTweet(socialPost);

        // test response data is what is expected
        assertEquals(socialPost, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.OK.getStatusCode() ,response.getStatus());
    }

    @Test
    final void postTweet_Twitter4JServiceExceptionThrownResponseBadRequest() throws Exception {
        String message = "testString";

        when(twitter4JService.sendTweet(socialPost.getMessage())).thenThrow(twitter4JServiceException);

        Response response = twitterResources.postTweet(socialPost);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    final void postTweet_NonTwitter4JServiceExceptionThrownResponseInternalServerError() throws Twitter4JServiceException {
        when(twitter4JService.sendTweet(tweet)).thenThrow(runtimeException);

        Response response = twitterResources.postTweet(socialPost);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
