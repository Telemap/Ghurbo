package com.mcc.ghurbo.login;

import android.os.Parcel;
import android.os.Parcelable;

public class LoginModel implements Parcelable{

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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getType() {
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userId);
        parcel.writeString(name);
        parcel.writeString(profilePic);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(type);
    }

    protected LoginModel(Parcel in) {
        userId = in.readString();
        name = in.readString();
        profilePic = in.readString();
        email = in.readString();
        phone = in.readString();
        type = in.readString();
    }

    public static final Creator<LoginModel> CREATOR = new Creator<LoginModel>() {
        @Override
        public LoginModel createFromParcel(Parcel in) {
            return new LoginModel(in);
        }

        @Override
        public LoginModel[] newArray(int size) {
            return new LoginModel[size];
        }
    };
}
