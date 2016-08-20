package vinformax.vinmart.model;

/**
 * Created by Mobtech-03 on 8/9/2016.
 */
public class UserData {

    private String username;
    private String useremail;
    private String userloginid;
    private String usercustid;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserloginid() {
        return userloginid;
    }

    public void setUserloginid(String userloginid) {
        this.userloginid = userloginid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsercustid() {
        return usercustid;
    }

    public void setUsercustid(String usercustid) {
        this.usercustid = usercustid;
    }
}