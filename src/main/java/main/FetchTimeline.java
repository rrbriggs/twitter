package main;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

public class FetchTimeline {
    private List<String> timeline;

    public FetchTimeline(Twitter twitter){
        try {
            timeline = twitter.getHomeTimeline().stream()
                    .map(item -> item.getText())
                    .collect(Collectors.toList());
            System.out.println(timeline + "\n");
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    public List<String> getTimeline() {
        return timeline;
    }
}
