package twitter_bootcamp.models;

import java.util.Date;

public class SocialPost {
    private String message;
    private Date createdAt;
    private SocialUser user;

    public SocialUser getSocialUser() { return user; }

    public void setSocialUser(SocialUser user) { this.user = user; }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
