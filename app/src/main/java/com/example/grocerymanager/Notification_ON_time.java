package com.example.grocerymanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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




                final ArrayList<String> list = new ArrayList();
//                final ArrayList<String> type = new ArrayList();
                final ArrayList<String> expiry = new ArrayList();
//                final ArrayList<String> quantity = new ArrayList();
//                final ArrayList<String> image = new ArrayList();

                //Expiry Date Pop UP
                String pattern = "mm/dd/yyyy";
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(pattern);
                Date today = Calendar.getInstance().getTime();
                String tod = df.format(today);


                Calendar calendar3 = Calendar.getInstance();
                calendar3.set(Calendar.HOUR_OF_DAY,0);
                calendar3.set(Calendar.MINUTE,0);
                calendar3.set(Calendar.SECOND,0);

                Intent intent3 = new Intent(getApplicationContext(),Notification_receiver_exp.class);
                PendingIntent pendingIntent3 = PendingIntent.getBroadcast(getApplication(),400,intent3,0);
                AlarmManager alarmManager3 = (AlarmManager)getSystemService(ALARM_SERVICE);

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("grocery_list");
                ref.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                            final grocery grocery_list = dataSnapshot.getValue(grocery.class);
                            final String exp = grocery_list.getDate();
                            expiry.add(exp);

                            if(exp==tod){
                                createNotificationChannel();
                                alarmManager3.set(AlarmManager.RTC_WAKEUP,calendar3.getTimeInMillis(),pendingIntent3);
                            }
                        }
                    }
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    private  void createNotificationChannel(){

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                            CharSequence name = "Notification expiry ";
                            String description = "Expiry reminder channel";
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel channel = new NotificationChannel("expiry", name, importance);
                            channel.setDescription(description);

                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });

            }
        });
    }


}