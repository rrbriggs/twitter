package twitter_bootcamp.services;

public class Twitter4JServiceException extends Exception {
    public Twitter4JServiceException() {}

    public Twitter4JServiceException(String message) {
        super(message);
    }
}
