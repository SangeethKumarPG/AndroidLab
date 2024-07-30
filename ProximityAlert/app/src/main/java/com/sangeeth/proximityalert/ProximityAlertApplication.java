package com.sangeeth.proximityalert;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class ProximityAlertApplication extends Application {

    public void onCreate(){
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {

        NotificationChannel notificationChannel = new NotificationChannel(
                "proximity_alert_channel",
                "Proximity Alert Channel",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel.setDescription("Channel for proximity alerts");
        }

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
