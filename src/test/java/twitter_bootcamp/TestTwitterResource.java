package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;



public class TestTwitterResource {

    TwitterResource twitterResource;

    @Mock
    Twitter twitter;

    @Mock
    Status mockStatus;

    @Mock ResponseList<Status> mockTwitterResponseList;

    @Mock
    TwitterException mockTwitterException;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        twitterResource = new TwitterResource(twitter);

        mockTwitterResponseList.add(mockStatus);
    }

    @Test
    final void testGetTwitterTimeline() throws TwitterException {
        when(twitter.getHomeTimeline()).thenReturn(mockTwitterResponseList);
        Response response = twitterResource.getTimeline();

        assertEquals(mockTwitterResponseList, response.getEntity());
    }

    @Test
    final void testGetTwitterTimelineStatus() throws TwitterException {
        when(twitter.getHomeTimeline()).thenReturn(mockTwitterResponseList);

        Response response = twitterResource.getTimeline();

        // on successful attempt, check for proper status code
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    final void getTimelineTwitterExceptionThrown_thenAssertionSucceeds() throws TwitterException {

        when(twitter.getHomeTimeline()).thenThrow(mockTwitterException);

        Response response = twitterResource.getTimeline();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void testPostTweet() throws TwitterException {
        String message = "Testing testPostTweet";

        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);

        Response response = twitterResource.postTweet(message);

        assertEquals(mockStatus, response.getEntity());
    }

    @Test
    final void testPostTweetStatus() throws TwitterException {
        String message = "Testing testPostTweet";

        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);

        Response response = twitterResource.postTweet(message);
        System.out.println();
        assertEquals(Response.Status.CREATED.getStatusCode() ,response.getStatus());
    }
}
