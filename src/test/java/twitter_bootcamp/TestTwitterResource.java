package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

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

    @Mock
    ResponseList<Status> mockTwitterResponseList;

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

        // test response data is what is expected
        assertEquals(mockTwitterResponseList, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    final void getTimelineTwitterExceptionThrown() throws TwitterException {

        when(twitter.getHomeTimeline()).thenThrow(mockTwitterException);

        Response response = twitterResource.getTimeline();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void testPostTweetResponseMatchesStatus() throws TwitterException {
        String message = "Testing testPostTweet";

        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);

        Response response = twitterResource.postTweet(message);

        // test response data is what is expected
        assertEquals(mockStatus, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.CREATED.getStatusCode() ,response.getStatus());
    }

    @Test
    final void testPostTweetExceptionThrown() throws TwitterException {
        String message = "Testing testPostTweet";

        when(twitter.updateStatus(anyString())).thenThrow(mockTwitterException);

        Response response = twitterResource.postTweet(message);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void testPostTweetCharLength() throws TwitterException {
        //build string of max tweet length + 1
        char[] charArray = new char[twitterResource.getTweetLength() + 1];
        String maxLenString = new String(charArray);

        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);

        Response response = twitterResource.postTweet(maxLenString);

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
