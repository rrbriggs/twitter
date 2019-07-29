package twitter_bootcamp.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;


public class AppConfiguration extends Configuration {

    @NotEmpty private String appName;

    @Valid
    @NotEmpty
    private TwitterAuth twitterAuth;

    @JsonProperty public String getAppName() {
        return appName;
    }

    @JsonProperty
    public void setAppName(final String appName) {
        this.appName = appName;
    }

    public TwitterAuth getTwitterAuth() {
        return twitterAuth;
    }

    public void setTwitterAuth(TwitterAuth twitterAuth) {
        this.twitterAuth = twitterAuth;
    }
}