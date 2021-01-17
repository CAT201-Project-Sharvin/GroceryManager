package com.example.grocerymanager;


import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Notification_receiver_break extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent repeating_intent = new Intent(context,Notification_activity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        //Builds the notification while in sync with the notification channel via the similar channel id
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"break")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_notification_overlay)
                .setContentTitle("GOOD MORNING")
                .setContentText("START YOUR DAY WITH COOL BREAKFAST SUGGESTIONS ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        //unique id to link with the channel to determine which notification this is suppose to be
        notificationManager.notify(100,builder.build());
    }
}
