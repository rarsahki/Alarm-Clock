package com.example.android.perfectclock;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

/**
 * Created by User on 05-12-2018.
 */

public class Notification_Initiation extends Application {
    public static final String CHANNEL_ID = "AlarmAlertService";

    @Override
    public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Alarm Alert Service", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
