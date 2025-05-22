package com.example.whowroteit;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AlarmActivity extends BaseActivity {
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        setupBottomNavigation();

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent notifyIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

        boolean alarmUp = pendingIntent != null;

        ToggleButton alarmToggle = findViewById(R.id.alarmToggle);
        alarmToggle.setChecked(alarmUp);

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if (isChecked) {
                    toastMessage = "Stand Up Alarm On!";
                    setAlarm();
                } else {
                    toastMessage = "Stand Up Alarm Off!";
                    cancelAlarm();
                    mNotificationManager.cancelAll();
                }

                Toast.makeText(AlarmActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

        createNotificationChannel();
    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Stand up notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void setAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
        long triggerTime = SystemClock.elapsedRealtime() + repeatInterval;

        if (alarmManager != null) {
            alarmManager.setInexactRepeating(
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    repeatInterval,
                    pendingIntent);
        }
    }

    private void cancelAlarm() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSelectedNavigationItem();
    }
}
