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

    private ListCache<SocialPost> listCache;

    private Twitter4JService() {}

    @Inject
    public Twitter4JService(Twitter twitter) {
        this.twitter = twitter;
        this.listCache = new ListCache();
    }

    public Optional<List<SocialPost>> getTwitterTimeline() throws Twitter4JServiceException {
        LOGGER.info("Getting Timeline.. ");
        try {
            List<SocialPost> socialPostList = cacheHomeTimeline();

            return Optional.of(socialPostList);
        }
        catch (TwitterException e) {
            LOGGER.error("Error getting twitter timeline. ", e);
            throw new Twitter4JServiceException();
        }
    }

    public Optional<List<SocialPost>> filterTimeline(String filterKey) throws TwitterException, Twitter4JServiceException {
        LOGGER.info("Filtering from Timeline using filterKey of {}", filterKey);

        if (listCache.getSocialPosts() == null){
            cacheHomeTimeline();
        }

        Optional<List<SocialPost>> filteredTimeline = Optional.of(listCache.getSocialPosts()
                .stream()
                .filter(status -> containsIgnoreCase(status.getMessage(), filterKey))
                .collect(toList()));

        if (filteredTimeline.get().isEmpty()) {
            throw new Twitter4JServiceException("");
        }

        return filteredTimeline;
    }

    public Optional<SocialPost> sendTweet(String message) throws Twitter4JServiceException, RuntimeException {
        try {
            // clear cache, it will be old after this update
            listCache.getSocialPosts().clear();

            return Optional.of(twitter.updateStatus(
                        Optional.of(message)
                                .filter(x -> x.length() <= MAX_TWEET_LENGTH)
                                .orElseThrow(() -> new Twitter4JServiceException("Maximum tweet length exceeded Ensure tweet is less than " + MAX_TWEET_LENGTH + " characters."))
                        ))
                    .map(this::socialPostBuilder);
        }
        catch (TwitterException e) {
            LOGGER.error("Unexpected error when calling twitter.updateStatus with the message of: {}", message, e);
            throw new RuntimeException();
        }
    }

    private List<SocialPost> cacheHomeTimeline() throws TwitterException {
        List<SocialPost> socialPostList = twitter.getHomeTimeline()
                .stream()
                .map(this::socialPostBuilder)
                .collect(toList());

        listCache.setSocialPosts(socialPostList);

        return socialPostList;
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

        return socialPost;
    }

    public void setListCache(ListCache listCache) {
        this.listCache = listCache;
    }

    public ListCache getListCache() {
        return listCache;
    }

    public static Twitter4JService getInstance() {
        return INSTANCE;
    }
}
