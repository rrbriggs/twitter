package twitter_bootcamp.services;

import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter_bootcamp.TwitterApp;
import twitter_bootcamp.models.SocialPost;
import twitter_bootcamp.models.SocialUser;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;

public final class Twitter4JService {

    private static final Twitter4JService INSTANCE = new Twitter4JService();

    private static final Logger LOGGER = LoggerFactory.getLogger(TwitterApp.class);

    protected static final int MAX_TWEET_LENGTH = 280;

    private Twitter twitter;

    private Cache<SocialPost> cache;

    private Twitter4JService() {}

    @Inject
    public Twitter4JService(Twitter twitter) {
        this.twitter = twitter;
        cache = new Cache<>();
        cache.InitializeCache();
    }

    public Optional<List<SocialPost>> getTwitterTimeline() throws Twitter4JServiceException {
        LOGGER.info("Getting Timeline.. ");

        if (cache.getCache("home") == null) {
            try {
                List<SocialPost> socialPostList = cacheTimeline("home");

                return Optional.of(socialPostList);
            }
            catch (TwitterException e) {
                LOGGER.error("Error getting twitter timeline. ", e);
                throw new Twitter4JServiceException();
            }
        }
        else {
            return Optional.of(cache.getCache("home"));
        }
    }

    public Optional<List<SocialPost>> getTwitterUserTimeline() throws Twitter4JServiceException {
        LOGGER.info("Getting Timeline.. ");

        if (cache.getCache("user") == null) {
            try {
                List<SocialPost> socialPostList = cacheTimeline("user");

                return Optional.of(socialPostList);
            }
            catch (TwitterException e) {
                LOGGER.error("Error getting twitter timeline. ", e);
                throw new Twitter4JServiceException();
            }
        }
        else {
            return Optional.of(cache.getCache("user"));
        }
    }

    public Optional<List<SocialPost>> filterTimeline(String filterKey) throws TwitterException, Twitter4JServiceException {
        LOGGER.info("Filtering from Timeline using filterKey of {}", filterKey);

        if (cache.getCache("home") == null){
            List<SocialPost> timelineFiltered = twitter.getHomeTimeline()
                    .stream()
                    .filter(status -> containsIgnoreCase(status.getText(), filterKey))
                    .map(this::socialPostBuilder)
                    .collect(toList());
            if (timelineFiltered.isEmpty()) {
                throw new Twitter4JServiceException("No filtered objects found");
            }
            else {
                LOGGER.info("Successfully filtered");

                return Optional.of(timelineFiltered);
            }
        }
        else {
            return Optional.of(cache.getCache("home")
                    .stream()
                    .filter(status -> containsIgnoreCase(status.getMessage(), filterKey))
                    .collect(toList()));
        }
    }

    public Optional<SocialPost> sendTweet(String message) throws Twitter4JServiceException, RuntimeException {
        try {
            return Optional.of(twitter.updateStatus(
                        Optional.of(message)
                                .filter(x -> x.length() <= MAX_TWEET_LENGTH)
                                .orElseThrow(() -> new Twitter4JServiceException("Maximum tweet length exceeded ensure tweet is less than " + MAX_TWEET_LENGTH + " characters."))
                        ))
                    .map(status -> {
                        cache.clearCache();
                        return socialPostBuilder(status);

                    });
        } catch (TwitterException e) {
            LOGGER.error("Unexpected error when calling twitter.updateStatus with the message of: {}", message, e);
            throw new RuntimeException();
        }
    }

    private List<SocialPost> cacheTimeline(String key) throws TwitterException {
        if (key == "home") {
            List<SocialPost> socialPostList = twitter.getHomeTimeline()
                    .stream()
                    .map(this::socialPostBuilder)
                    .collect(toList());

            cache.setCache(key, socialPostList);

            return socialPostList;
        } else {
            List<SocialPost> socialPostList = twitter.getUserTimeline()
                    .stream()
                    .map(this::socialPostBuilder)
                    .collect(toList());

            cache.setCache(key, socialPostList);

            return socialPostList;
        }
    }

    protected SocialPost socialPostBuilder(Status status) {
        SocialPost socialPost = new SocialPost();
        SocialUser socialUser = new SocialUser();

        socialUser.setName(status.getUser().getName());
        socialUser.setTwitterHandle(status.getUser().getScreenName());
        socialUser.setProfileImageUrl(status.getUser().getProfileImageURL());

        socialPost.setSocialUser(socialUser);
        socialPost.setCreatedAt(status.getCreatedAt());
        socialPost.setMessage(status.getText());
        socialPost.setPostID(Long.toString(status.getId()));

        return socialPost;
    }

    public static Twitter4JService getInstance() {
        return INSTANCE;
    }
}
