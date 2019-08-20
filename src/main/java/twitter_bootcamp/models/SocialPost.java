package twitter_bootcamp.models;

import java.util.Date;

public class SocialPost {
    private String message;
    private Date createdAt;
    private SocialUser user;
    private String postID;

    public String getPostID() { return postID; }

    public void setPostID(String postURL) { this.postID = postURL; }

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
