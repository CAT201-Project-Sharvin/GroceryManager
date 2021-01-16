package com.example.grocerymanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_receiver_exp extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"expiry")
           //    .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("WARNING")
                .setContentText("One of your Grocery has expired ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
             //   .setAutoCancel(true);
      //  notificationManager.notify(400, builder.build());
        Intent notionce = new Intent(context,Expiry_activity.class);//Direct to Katheraven Line
        notionce.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(400,builder.build());
    }
}
