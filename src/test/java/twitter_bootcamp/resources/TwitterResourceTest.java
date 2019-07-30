package twitter_bootcamp.resources;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.User;
import twitter_bootcamp.services.Twitter4JService;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class TwitterResourceTest {

    TwitterResource twitterResource;

    @Mock
    Twitter4JService twitter4JService;

    @Mock
    Status mockStatus;

    @Mock
    User user;

    @Mock
    ResponseList<Status> mockTwitterResponseList;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        twitterResource = new TwitterResource(twitter4JService);

        mockTwitterResponseList.add(mockStatus);
    }

    @Test
    final void testGetTwitterTimeline() {
        when(twitter4JService.getTwitterTimeline()).thenReturn(mockTwitterResponseList);
        Response response = twitterResource.getTimeline();

        // test response data is what is expected
        assertEquals(mockTwitterResponseList, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    final void getTimelineTwitterResponseListReturnedNull() {

        when(twitter4JService.getTwitterTimeline()).thenReturn(null);

        Response response = twitterResource.getTimeline();

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }

    @Test
    final void testPostTweetResponseMatchesStatus() {
        String message = "Testing testPostTweet";

        when(twitter4JService.sendTweet(anyString())).thenReturn(mockStatus);
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        Response response = twitterResource.postTweet(message);

        // test response data is what is expected
        assertEquals(mockStatus, response.getEntity());
        // test status code is what is expected
        assertEquals(Response.Status.CREATED.getStatusCode() ,response.getStatus());
    }

    @Test
    final void testPostTweetStatusReturnNull() {

        when(twitter4JService.sendTweet(anyString())).thenReturn(null);

        Response response = twitterResource.postTweet(anyString());

        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), response.getStatus());
    }
}
