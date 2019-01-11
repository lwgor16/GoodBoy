package my.edu.tarc.goodboy;

public class User {
    private String userName;
    private String userPassword;
    private String userRealName;
    private String userAddress;
    private String userContactNumber;
    private int userAge;
    private String userGender;
    private String userICNumber;
    private int userAccountType;

    public User(String userName, String userPassword, String userRealName, String userAddress, String userContactNumber, int userAge, String userGender, String userICNumber, int userAccountType) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRealName = userRealName;
        this.userAddress = userAddress;
        this.userContactNumber = userContactNumber;
        this.userAge = userAge;
        this.userGender = userGender;
        this.userICNumber = userICNumber;
        this.userAccountType = userAccountType;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserICNumber() {
        return userICNumber;
    }

    public void setUserICNumber(String userICNumber) {
        this.userICNumber = userICNumber;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public int getUserAccountType() {
        return userAccountType;
    }

    public void setUserAccountType(int userAccountType) {
        this.userAccountType = userAccountType;
    }
}
