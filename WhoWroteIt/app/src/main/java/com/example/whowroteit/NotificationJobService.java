package com.example.whowroteit;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.core.app.NotificationCompat;

public class NotificationJobService extends JobService {
    NotificationManager mNotificationManager;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    @Override
    public boolean onStartJob(JobParameters params) {
        createNotificationChannel();

        PendingIntent contentPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, NotificationSchedulerActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_job_running)
                .setContentTitle("Job Service")
                .setContentText("Your Job is running!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotificationManager.notify(0, notificationBuilder.build());

        return false;
    }


    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    public void createNotificationChannel(){
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Job Service notification",
                    NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Job Service");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }
}
