package twitter_bootcamp.services;

import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;

import java.util.ArrayList;

public class CustomResponseList<T> extends ArrayList<T> implements ResponseList<T> {
    @Override
    public RateLimitStatus getRateLimitStatus() {
        return null;
    }

    @Override
    public int getAccessLevel() {
        return 1;
    }
}
