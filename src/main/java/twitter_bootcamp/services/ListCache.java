package twitter_bootcamp.services;

import java.util.List;
import java.util.Optional;


public class ListCache<T> {
    private Optional<List<T>> socialPosts;

    public Optional<List<T>> getSocialPosts() {
        return socialPosts;
    }

    public void setSocialPosts(Optional<List<T>> socialPosts) {
        this.socialPosts = socialPosts;
    }
}