package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import twitter4j.*;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;


public class TestTwitterResource {

    TwitterResource twitterResource;

    @Mock ResponseList twitterList;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        twitterResource = new TwitterResource(){
            @Override
            public TwitterResponse getTwitterResponse() {
                return twitterList;
            }
        };
    }

    // TODO: This currently isn't mocking the timeline
    @Test
    final void testGetTwitterTimelineStatus() throws TwitterException {

        // when(twitterInstance.getTwitter().getHomeTimeline()).thenReturn(twitterList);

        Response response = twitterResource.getTimeline();

        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    final void testGetTwitterTimeline() {
        // when(twitterInstance.getTwitter().getHomeTimeline()).thenReturn(twitterList);
        Response response = twitterResource.getTimeline();
        System.out.println(response.getEntity());
        // when(response.getHomeTimeline()).thenReturn(twitterList);
        // response.getHomeTimeline();
        assertNotNull(response.getEntity());
    }
}
