package main;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

public class FetchTimeline {
    private List<String> timeline;

    public FetchTimeline(Twitter twitter){
        try {
            timeline = twitter.getHomeTimeline().stream()
                    .map(item -> processTweetNameAndText(item))
                    .collect(Collectors.toList());
//            System.out.println(timeline + "\n");
            System.out.println(twitter.getHomeTimeline());

        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private String processTweetNameAndText(Status item) {
        String tweetString = item.getUser().getName() + ": " + item.getText();
        return tweetString;
    }

    public List<String> getTimeline() {
        return timeline;
    }
}
