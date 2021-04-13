package com.akkiei.cryptoticker;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class ForegroundService extends Service {
    public void ForegroundServiceStart() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification notification =
                new Notification.Builder(this, "200")
                        .setContentTitle("CryptoTicker")
                        .setContentText("CryptoTicker Service Running!")
                        .setSmallIcon(R.drawable.ic_launcher_my_icon_background)
                        .setContentIntent(pendingIntent)
                        .setTicker("Designed by Akkiei")
                        .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "100";
            String description = "Foreground service channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("200", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = this.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(200, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Welcome to CryptoTicker", Toast.LENGTH_SHORT).show();
        ForegroundServiceStart();
        return Service.START_STICKY;
    }
}
