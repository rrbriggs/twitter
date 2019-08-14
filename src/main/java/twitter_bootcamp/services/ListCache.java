package twitter_bootcamp.services;

import java.util.List;


public class ListCache<T> {
    private List<T> socialPosts;

    public List<T> getSocialPosts() {
        return socialPosts;
    }

    public void setSocialPosts(List<T> socialPosts) {
        this.socialPosts = socialPosts;
    }
}