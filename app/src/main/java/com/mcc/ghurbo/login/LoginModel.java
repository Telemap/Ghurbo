package com.mcc.ghurbo.login;

public class LoginModel {

    public static final String TYPE_FACEBOOK = "facebook";
    public static final String TYPE_GOOGLE = "google";
    public static final String TYPE_ACCOUNT_KIT = "acc-kit";

    private String userId;
    private String name;
    private String profilePic;
    private String email;
    private String phone;
    private String type;

    public LoginModel(String userId, String name, String profilePic, String email, String phone, String type) {
        this.userId = userId;
        this.name = name;
        this.profilePic = profilePic;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getType() {
        return type;
    }
}
