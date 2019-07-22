package twitter_bootcamp;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;


public class TestTwitterResource {

    ResponseList twitterList;

    TwitterResource twitterResource;

    @InjectMocks
    GetTwitterInstance twitterInstance;

    @Mock
    Twitter mockTwitter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        twitterResource = new TwitterResource();
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
        // when(response.getHomeTimeline()).thenReturn(twitterList);
        // response.getHomeTimeline();
        assertNotNull(response.getEntity());
    }




















//    TODO: clean up (keeping around for reference atm)
//    @ClassRule
//    public static final ResourceTestRule resources = ResourceTestRule.builder()
//            .addResource(new TwitterResource())
//            .build();
//
//    @Test
//    public void testResource() {
//        assertThat(resources.target("/api/1.0/twitter")
//                .request()
//                .get()
//                .isEqualTo());
//
//    }
}
