package main;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

public class FetchTimeline {
    public FetchTimeline(Twitter twitter){
        List<String> timeLine;

        try {
            timeLine = twitter.getHomeTimeline().stream()
                    .map(item -> item.getText())
                    .collect(Collectors.toList());
            System.out.println(timeLine + "\n");
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}