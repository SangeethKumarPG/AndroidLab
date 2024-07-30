package com.sangeeth.proximityalert;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent){
        String message = intent.getStringExtra("message");
        int type = intent.getIntExtra(LocationManager.KEY_PROXIMITY_ENTERING,0);
        String proximityType = (type == 1)? "Entering":"Exiting";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "proximity_alert_channel")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Proximity Alert")
                .setContentText(message+"("+proximityType+")");
        NotificationManager notificationManager =(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100,builder.build());


    }
}
