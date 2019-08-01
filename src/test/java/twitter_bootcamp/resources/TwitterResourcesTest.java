package twitter_bootcamp.resources;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;
import twitter_bootcamp.services.Twitter4JService;
import twitter_bootcamp.services.Twitter4JServiceException;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
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
    ResponseList<Status> mockTwitterResponseList;

    @Mock
    Twitter4JServiceException twitter4JServiceException;

    @Mock
    RuntimeException runtimeException;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        twitterResources = new TwitterResources(twitter4JService);

        mockTwitterResponseList.add(mockStatus);
    }

    @Test
    final void getTimeline_ResponseMatchesStatus() throws Exception {
        when(twitter4JService.getTwitterTimeline()).thenReturn(mockTwitterResponseList);
        Response response = twitterResources.getTimeline();

        // test response data is what is expected
        assertEquals(mockTwitterResponseList, response.getEntity());
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
    final void postTweet_ResponseMatchesStatus() throws Exception {
        String message = "Testing testPostTweet";

        when(twitter4JService.sendTweet(anyString())).thenReturn(mockStatus);
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        Response response = twitterResources.postTweet(message);

        // test response data is what is expected
        assertEquals(mockStatus, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.CREATED.getStatusCode() ,response.getStatus());
    }

    @Test
    final void postTweet_Twitter4JServiceExceptionThrownResponseBadRequest() throws Exception {

        when(twitter4JService.sendTweet(anyString())).thenThrow(twitter4JServiceException);

        Response response = twitterResources.postTweet(anyString());

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    final void postTweet_NonTwitter4JServiceExceptionThrownResponseInternalServerError() throws Twitter4JServiceException {
        when(twitter4JService.sendTweet(anyString())).thenThrow(runtimeException);

        Response response = twitterResources.postTweet(anyString());

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
