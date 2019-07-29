package twitter_bootcamp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class TwitterAuth {
    @NotEmpty
    private String consumerKey;
    @NotEmpty private String consumerSecretKey;
    @NotEmpty private String accessToken;
    @NotEmpty private String accessTokenSecret;

    public TwitterAuth() {}

    public void setConsumerKey(String consumerKey) { this.consumerKey = consumerKey; }
    public void setConsumerSecretKey(String consumerSecretKey) { this.consumerSecretKey = consumerSecretKey; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }
    public void setAccessTokenSecret(String accessTokenSecret) { this.accessTokenSecret = accessTokenSecret; }

    @JsonProperty public String getConsumerKey() { return consumerKey; }
    @JsonProperty public String getConsumerSecretKey() { return consumerSecretKey; }
    @JsonProperty public String getAccessToken() { return accessToken; }
    @JsonProperty public String getAccessTokenSecret() { return accessTokenSecret; }
}
