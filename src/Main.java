import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.io.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    private static String CONSUMER_KEY;
    private static String CONSUMER_SECRET_KEY;
    private static String ACCESS_TOKEN;
    private static String ACCESS_TOKEN_SECRET;

    public static void main(String[] args) throws IOException {

        // populate tokens and keys from properties file
        loadConfig();

        System.out.println("Welcome to the BrigBot Twitter Interface!");

        int selection = 0;

        while (selection != 3) {
            System.out.println("Select from the following:\n 1: Write a tweet\n 2: View Timeline\n 3: Exit");
            Scanner sc = new Scanner(System.in);
            selection = sc.nextInt();

            switch (selection) {
                case 1: // Write a Tweet
                    System.out.println("Write a tweet:\n");
                    Scanner tweetInput = new Scanner(System.in);
                    String tweet = tweetInput.nextLine();
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

    // populate keys and tokens from Config.properties
    private static void loadConfig() throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream("Config.properties");
        properties.load(inputStream);
        CONSUMER_KEY = properties.getProperty("CONSUMER_KEY");
        CONSUMER_SECRET_KEY = properties.getProperty("CONSUMER_SECRET_KEY");
        ACCESS_TOKEN = properties.getProperty("ACCESS_TOKEN");
        ACCESS_TOKEN_SECRET = properties.getProperty("ACCESS_TOKEN_SECRET");
    }

    private static Twitter getTwitterInstance() {
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