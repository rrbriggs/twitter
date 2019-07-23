package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import twitter4j.*;

import javax.ws.rs.core.Response;


public class TestTwitterResource {

    TwitterResource twitterResource;

    @Mock
    ResponseList twitterList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        twitterResource = new TwitterResource() {
            @Override
            public TwitterResponse getTwitterResponse() {
                return twitterList;
            }
        };
    }

    @Test
    final void testGetTwitterTimelineStatus() throws TwitterException {
        Response response = twitterResource.getTimeline();

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    final void testGetTwitterTimeline() {
        Response response = twitterResource.getTimeline();
        System.out.println(response.getEntity());

        assertNotNull(response.getEntity());
    }
}
