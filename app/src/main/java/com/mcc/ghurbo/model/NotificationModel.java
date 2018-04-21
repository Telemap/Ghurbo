package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class NotificationModel implements Parcelable {

    private int id;
    private String title;
    private String body;
    private String image;
    private String status;

    public NotificationModel(int id, String title, String body,
                             String image, String status) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.image = image;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getImage() {
        return image;
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeString(image);
        parcel.writeString(status);
    }

    protected NotificationModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        body = in.readString();
        image = in.readString();
        status = in.readString();
    }

    public static final Creator<NotificationModel> CREATOR = new Creator<NotificationModel>() {
        @Override
        public NotificationModel createFromParcel(Parcel in) {
            return new NotificationModel(in);
        }

        @Override
        public NotificationModel[] newArray(int size) {
            return new NotificationModel[size];
        }
    };
}
