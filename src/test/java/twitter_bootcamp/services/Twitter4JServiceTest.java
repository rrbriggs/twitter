package twitter_bootcamp.services;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import twitter4j.*;
import twitter_bootcamp.config.AppConfiguration;
import twitter_bootcamp.models.TwitterUser;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class Twitter4JServiceTest {

    Twitter4JService twitter4JService;

    // todo: name change
    ResponseList<Status> mockTwitterResponseList = null;
    List<TwitterUser> twitterUserList = new ArrayList<>();

    @Mock
    Twitter twitter;

    @Mock
    Status mockStatus;

    @Mock
    User user;

    @Mock
    TwitterUser twitterUser;


    @Mock
    AppConfiguration configuration;

    @Mock
    TwitterException mockTwitterException;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);

        mockTwitterResponseList.add(mockStatus);

        twitterUserList.add(twitterUser);

        twitter4JService = new Twitter4JService(twitter, configuration);

    }

    @Test
    final void getTwitterTimeline_ReturnsResponseList() throws Twitter4JServiceException, TwitterException {
        when(twitter.getHomeTimeline()).thenReturn(mockTwitterResponseList);

        when(mockStatus.getUser()).thenReturn(user);
        when(mockStatus.getCreatedAt()).thenReturn(new Date());
        when(mockStatus.getText()).thenReturn("Test Message");
        when(user.getName()).thenReturn("Test User");
        when(user.getScreenName()).thenReturn("Test Handle");
        when(user.getProfileImageURL()).thenReturn("Test Profile Image URL ");

        // test response data is what is expected
        assertEquals(twitterUserList, twitter4JService.getTwitterTimeline());
    }

    @Test
    final void getTwitterTimeline_ThrowsTwitterException() throws TwitterException {
        when(twitter.getHomeTimeline()).thenThrow(mockTwitterException);

        assertThrows(Twitter4JServiceException.class, () -> {
            twitter4JService.getTwitterTimeline();
        });
    }

    @Test
    final void sendTweet_ReturnsStatus() throws TwitterException {
        when(twitter.updateStatus(anyString())).thenReturn(mockStatus);
        when(mockStatus.getUser()).thenReturn(user);
        when(user.getName()).thenReturn("Test User");

        assertEquals(mockStatus, twitter.updateStatus(anyString()));
    }

    @Test
    final void sendTweet_MessageLengthExceededThrowsTwitter4JServiceException() {
        char[] charArray = new char[twitter4JService.getMaxTweetLength() + 1];
        String exceedsMaxLenString = new String(charArray);

        assertThrows(Twitter4JServiceException.class, () -> {
            twitter4JService.sendTweet(exceedsMaxLenString);
        });
    }

    @Test
    final void sendTweet_ErrorInUpdateStatusThrowsRuntimeError() throws TwitterException {
        when(twitter.updateStatus(anyString())).thenThrow(mockTwitterException);

        assertThrows(RuntimeException.class, () -> {
            twitter4JService.sendTweet(anyString());
        });
    }
}
