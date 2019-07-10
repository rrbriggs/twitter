import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class AppLogic {
    public AppLogic() throws IOException {
        int selection = 0;

        System.out.println("Welcome to the BrigBot Twitter Interface!");


        while (selection != 3) {
            System.out.println("Select from the following:\n 1: Write a tweet\n 2: View Timeline\n 3: Exit");
            Scanner sc = new Scanner(System.in);
            selection = sc.nextInt();
            sc.close();

            switch (selection) {
                case 1: // Write a Tweet
                    System.out.println("Write a tweet:\n");
                    Scanner tweetInput = new Scanner(System.in);
                    String tweet = tweetInput.nextLine();
                    tweetInput.close();
                    PostTweet postTweet = new PostTweet(getTwitterInstance(), tweet);
                    break;
                case 2: // View Timeline
                    FetchTimeline fetchTimeline = new FetchTimeline(getTwitterInstance());
                    break;
                case 3: // exit condition
                    break;
                default:
                    System.out.println("Input not valid, please try again.");
            }
        }
    }

    private Twitter getTwitterInstance() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("Config.properties");
        properties.load(inputStream);

        // populate tokens and keys from properties file
        String CONSUMER_KEY = properties.getProperty("CONSUMER_KEY");
        String CONSUMER_SECRET_KEY = properties.getProperty("CONSUMER_SECRET_KEY");
        String ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
        String ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");

        inputStream.close();

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(CONSUMER_KEY)
                .setOAuthConsumerSecret(CONSUMER_SECRET_KEY)
                .setOAuthAccessToken(ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(ACCESS_TOKEN_SECRET);

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        return twitter;
    }
}
