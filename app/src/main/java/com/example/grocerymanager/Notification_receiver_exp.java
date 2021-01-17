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
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("WARNING")
                .setContentText("One of your Grocery has expired ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
      //  notificationManager.notify(400, builder.build());
        Intent notionce = new Intent(context,listGrocery.class);
        notionce.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(400,builder.build());
    }
}
