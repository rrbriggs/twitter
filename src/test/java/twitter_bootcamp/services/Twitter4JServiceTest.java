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

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


public class Twitter4JServiceTest {

    Twitter4JService twitter4JService;

    ResponseList<Status> responseList;


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

        responseList = new CustomResponseList<>();

        twitter4JService = new Twitter4JService(twitter, configuration);

        mockTwitterResponseList.add(mockStatus);
    }

    @Test
    final void getTwitterTimeline_ReturnsResponseLisPassesOnMessageEquivalent() throws Twitter4JServiceException, TwitterException {
        // using deprecated Date type due to twitter4j using Date type
        Date date = new Date(1, 1, 2019);

        when(mockStatus.getUser()).thenReturn(user);
        when(mockStatus.getCreatedAt()).thenReturn(date);
        when(mockStatus.getText()).thenReturn("Test Message");
        when(user.getName()).thenReturn("Test User");
        when(user.getScreenName()).thenReturn("Test Handle");
        when(user.getProfileImageURL()).thenReturn("Test Profile Image URL ");

        responseList.add(mockStatus);

        when(twitter.getHomeTimeline()).thenReturn(responseList);

        assertEquals(responseList.get(0).getText(), twitter4JService.getTwitterTimeline().get(0).getMessage());
    }

    @Test
    final void getTwitterTimeline_ThrowsTwitterException() throws TwitterException, Twitter4JServiceException {
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
        char[] charArray = new char[twitter4JService.MAX_TWEET_LENGTH + 1];
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
