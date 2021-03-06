package at.punkt.tweetcache.domain;

import java.util.Date;

/**
 * 
 * @author Thomas Fritz <fritz@punkt.at>
 */
public class Tweet {

    private long id;
    private String fromUser;
    private String isoLanguageCode;
    private String profileImageUrl;
    private String source;
    private String text;
    private long toUserId = -1;
    private Date createdAt;
    private long fromUserId;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public void setIsoLanguageCode(String isoLanguageCode) {
        this.isoLanguageCode = isoLanguageCode;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setid(long id) {
        this.id = id;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getFromUser() {
        return fromUser;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public String getIsoLanguageCode() {
        return isoLanguageCode;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getSource() {
        return source;
    }

    public String getText() {
        return text;
    }

    public long getid() {
        return id;
    }

    public long getToUserId() {
        return toUserId;
    }

    @Override
    public String toString() {
        return "Tweet{" + "id=" + id + ", fromUser=" + fromUser + ", isoLanguageCode=" + isoLanguageCode + ", profileImageUrl=" + profileImageUrl + ", source=" + source + ", text=" + text + ", toUserId=" + toUserId + ", createdAt=" + createdAt + ", fromUserId=" + fromUserId + '}';
    }
}
