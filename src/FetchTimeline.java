import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.List;
import java.util.stream.Collectors;

public class FetchTimeline {
    public FetchTimeline(Twitter twitter){
        System.out.println(getTimeLine(twitter));
    }

    private static List<String> getTimeLine(Twitter twitter) {
        try {
            return twitter.getHomeTimeline().stream()
                    .map(item -> item.getText())
                    .collect(Collectors.toList());
        }
        catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
