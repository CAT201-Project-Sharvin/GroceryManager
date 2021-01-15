package com.example.grocerymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class Notification_ON_time extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification__o_n_time);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lunch Notification
                Calendar calendar = Calendar.getInstance();
                //if (calendar.getTime().compareTo(new Date()) < 0) calendar.add(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 11);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);

                Intent intent = new Intent(getApplicationContext(),Notification_receiver_lunch.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

                // Breakfast Notification
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR_OF_DAY,7);
                calendar1.set(Calendar.MINUTE,0);
                calendar1.set(Calendar.SECOND,0);

                Intent intent1 = new Intent(getApplicationContext(),Notification_receiver_break.class);
                PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getApplication(),200,intent1,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager1 = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager1.setRepeating(AlarmManager.RTC_WAKEUP,calendar1.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent1);


                //Dinner Notification
                Calendar calendar2 = Calendar.getInstance();
                calendar2.set(Calendar.HOUR_OF_DAY,18);
                calendar2.set(Calendar.MINUTE,0);
                calendar2.set(Calendar.SECOND,0);

                Intent intent2 = new Intent(getApplicationContext(),Notification_receiver_Dinner.class);
                PendingIntent pendingIntent2 = PendingIntent.getBroadcast(getApplication(),300,intent2,PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager2 = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager2.setRepeating(AlarmManager.RTC_WAKEUP,calendar2.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent2);


                //Expiry Date Pop UP


            }
        });
    }
}