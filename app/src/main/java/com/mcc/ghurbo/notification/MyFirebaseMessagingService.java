package com.mcc.ghurbo.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.activity.MainActivity;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.sqlite.DbConstants;

import java.util.Map;

public class MyFirebaseMessagingService  extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Map<String, String> params = remoteMessage.getData();
            sendNotification(params.get("title"), params.get("body"));
        }
    }

    private void sendNotification(String title, String messageBody) {

        insertNotification(title, messageBody, ""); // TODO: Implement push notification image
        broadcastNewNotification();
        if (AppPreference.getInstance(MyFirebaseMessagingService.this).isNotificationOn()) {

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String CHANNEL_ID = "noti01";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setVibrate(new long[]{1000, 1000})
                    .setSound(defaultSoundUri)
                    .setChannelId(CHANNEL_ID)
                    .setContentIntent(pendingIntent);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
            }

            notificationManager.notify(774, notificationBuilder.build());
        }
    }

    private void broadcastNewNotification() {
        Intent intent = new Intent(AppConstants.NEW_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    public void insertNotification(String title, String body, String image) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_TITLE, title);
        values.put(DbConstants.COLUMN_BODY, body);
        values.put(DbConstants.COLUMN_IMAGE, image);
        values.put(DbConstants.COLUMN_STATUS, AppConstants.STATUS_UNSEEN);

        getContentResolver().insert(DbConstants.NOTI_CONTENT_URI, values);
    }
}