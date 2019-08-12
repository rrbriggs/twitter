package twitter_bootcamp.services;

import java.util.List;
import twitter_bootcamp.models.SocialPost;


public class SocialPostListCache {
    private List<SocialPost> socialPosts;

    // todo: get rid of this once it is working
//    public SocialPostListCache(List<SocialPost> socialPosts) {
//        this.socialPosts = socialPosts;
//    }

    public List<SocialPost> getSocialPosts() {
        return socialPosts;
    }

    public void setSocialPosts(List<SocialPost> socialPosts) {
        this.socialPosts = socialPosts;
    }
}