package twitter_bootcamp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfiguration extends Configuration {

    @NotEmpty private String appName;

    @NotEmpty private String consumerKey;
    @NotEmpty private String consumerSecretKey;
    @NotEmpty private String accessTokenSecret;
    @NotEmpty private String accessToken;



    @JsonProperty public String getAppName() {
        return appName;
    }

    @JsonProperty public String getConsumerKey() { return consumerKey; }
    @JsonProperty public String getConsumerSecretKey() { return consumerSecretKey; }
    @JsonProperty public String getAccessTokenSecret() { return accessTokenSecret; }
    @JsonProperty public String getAccessToken() { return accessToken; }

    @JsonProperty
    protected void setConsumerKey(final String consumerKey) {
        this.consumerKey = consumerKey;
    }

    @JsonProperty
    public void setConsumerSecretKey(final String consumerSecretKey) {
        this.consumerSecretKey = consumerSecretKey;
    }

    @JsonProperty
    public void setAccessTokenSecret(final String accessTokenSecret) {
        this.accessTokenSecret = accessTokenSecret;
    }

    @JsonProperty
    public void setAccessToken(final String accessToken) {
        this.accessToken = accessToken;
    }

    @JsonProperty
    public void setAppName(final String appName) {
        this.appName = appName;
    }


}
