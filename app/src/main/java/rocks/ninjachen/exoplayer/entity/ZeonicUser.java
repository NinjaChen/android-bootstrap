package rocks.ninjachen.exoplayer.entity;

/**
 * Created by ninja on 1/13/17.
 */
public class ZeonicUser {

    /**
     * The url of user portrait
     * zeonic-key is "profile_image"
     */
    public final static String USER_PORTRAIT_KEY = "profile_image";
    protected String userPortrait;

    protected String username;

    /**
     * The display name of the user
     * zeonic-key is "user_name"
     */
        public final static String NICK_NAME_KEY = "user_name";
    protected String nickname;
//    protected String objectId;
//    boolean isNewUser = false;
    // if logout save username to next login
    boolean isLogin = false;

    ThirdPartUserType userType = null;
//    protected String firstName;
//    protected String lastName;
//    protected String phone;
//    protected String sessionToken;
//    protected String gravatarId;
//    protected String avatarUrl;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getObjectId() {
//        return objectId;
//    }
//
//    public void setObjectId(String objectId) {
//        this.objectId = objectId;
//    }
//
//    public boolean isNewUser() {
//        return isNewUser;
//    }
//
//    public void setNewUser(boolean newUser) {
//        isNewUser = newUser;
//    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(String userPortrait) {
        this.userPortrait = userPortrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ThirdPartUserType getUserType() {
        return userType;
    }

    public void setUserType(ThirdPartUserType userType) {
        this.userType = userType;
    }

    public enum ThirdPartUserType {
        WECHAT, WEIBO
    }
}
