package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.TwitterResponse;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


import javax.ws.rs.core.Response;


public class TestTwitterResource {

    TwitterResource twitterResource;

    @Mock
    ResponseList twitterList;

    @BeforeEach
    void setUp() {

        // return mocked twitterList when getTwitterResponse() is called
        twitterResource = new TwitterResource(){
            @Override
            public TwitterResponse getTwitterResponse() {
                return twitterList;
            }
        };
    }

    @Test
    final void testGetTwitterTimeline() {
        Response response = twitterResource.getTimeline();

        assertNotNull(response);
    }

    @Test
    final void testGetTwitterTimelineStatus() {
        Response response = twitterResource.getTimeline();

        // on successful attempt, check for proper status code
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }
}
