package services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.services.Twitter4JService;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class Twitter4JServiceTest {

    Twitter4JService twitter4JService;

    @Mock
    Twitter twitter;

    @Mock
    TwitterFactory twitterFactory;

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
    final void getTwitterTimeline_ReturnsResponseList() throws TwitterException {
        when(twitter.getHomeTimeline()).thenReturn(mockTwitterResponseList);

        // test response data is what is expected
        assertEquals(mockTwitterResponseList, twitter4JService.getTwitterTimeline());
    }

    @Test
    final void getTwitterTimeline_ThrowsTwitterException() throws TwitterException {
        when(twitter.getHomeTimeline()).thenThrow(mockTwitterException);

        assertEquals(null, twitter4JService.getTwitterTimeline());
    }

    @Test
    final void sendTweet_ReturnsStatus() throws TwitterException {
        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        assertEquals(mockStatus, twitter.updateStatus(anyString()));
    }

    @Test
    final void sendTweet_MessageLengthExceededReturnsNull() {
        char[] charArray = new char[twitter4JService.getMaxTweetLength() + 1];
        String exceedsMaxLenString = new String(charArray);

        assertEquals(null, twitter4JService.sendTweet(exceedsMaxLenString));
    }

    @Test
    final void sendTweet_MessageTwitterExceptionThrownReturnsNull() throws TwitterException {
        when(twitter.updateStatus(anyString())).thenThrow(mockTwitterException);

        assertEquals(null, twitter4JService.sendTweet(anyString()));
    }
}
