package twitter_bootcamp.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;
import twitter4j.TwitterException;
import twitter_bootcamp.config.AppConfiguration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class Twitter4JServiceTest {

    Twitter4JService twitter4JService;

    @Mock
    Twitter twitter;

    @Mock
    Status mockStatus;

    @Mock
    User user;

    @Mock
    AppConfiguration configuration;

    @Mock
    ResponseList<Status> mockTwitterResponseList;

    @Mock
    TwitterException mockTwitterException;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        twitter4JService = new Twitter4JService(twitter, configuration);

        mockTwitterResponseList.add(mockStatus);
    }

    @Test
    final void getTwitterTimeline_ReturnsResponseList() throws Exception {
        when(twitter.getHomeTimeline()).thenReturn(mockTwitterResponseList);

        // test response data is what is expected
        assertEquals(mockTwitterResponseList, twitter4JService.getTwitterTimeline());
    }

    @Test
    final void getTwitterTimeline_ThrowsTwitterException() throws TwitterException, Twitter4JServiceException {
        when(twitter.getHomeTimeline()).thenThrow(mockTwitterException);
        //System.out.println(twitter4JService.getTwitterTimeline());

        assertThrows(Twitter4JServiceException.class, () -> {
            twitter4JService.getTwitterTimeline();
        });
    }

    @Test
    final void sendTweet_ReturnsStatus() throws TwitterException, Twitter4JServiceException {
        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        assertEquals(mockStatus, twitter4JService.sendTweet(anyString()));
    }

    @Test
    final void sendTweet_MessageLengthExceededThrowsTwitter4JServiceException() throws Twitter4JServiceException {
        char[] charArray = new char[twitter4JService.MAX_TWEET_LENGTH + 1];
        String exceedsMaxLenString = new String(charArray);

        assertThrows(Twitter4JServiceException.class, () -> {
            twitter4JService.sendTweet(exceedsMaxLenString);
        });
    }

    @Test
    final void sendTweet_ErrorInUpdateStatusThrowsRuntimeError() throws Twitter4JServiceException, TwitterException {
        when(twitter.updateStatus(anyString())).thenThrow(mockTwitterException);

        assertThrows(RuntimeException.class, () -> {
            twitter4JService.sendTweet(anyString());
        });
    }
}
