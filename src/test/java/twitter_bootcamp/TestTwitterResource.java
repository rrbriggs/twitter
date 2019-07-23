package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterResponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


import javax.ws.rs.core.Response;
import java.io.IOException;


public class TestTwitterResource {

    TwitterResource twitterResource;

    @Mock
    ResponseList twitterList;

    @Mock
    TwitterResponse twitterResponse;

    @Mock
    GetTwitterInstance getTwitterInstance;

    @Mock
    Twitter twitter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        twitterResource = new TwitterResource() {
            @Override
            public Twitter getTwitter() {
                return twitter;
            }
        };
    }

    @Test
    final void testGetTwitterTimelineStatus() throws TwitterException {
        when(getTwitterInstance.getTwitterTimeline()).thenReturn(twitterResponse);
        Response response = twitterResource.getTimeline();

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    final void testGetTwitterTimeline() throws TwitterException {
        when(getTwitterInstance.getTwitterTimeline()).thenReturn(twitterResponse);

        Response response = twitterResource.getTimeline();
        System.out.println(response);

        assertNotNull(response.getEntity());
    }
}
