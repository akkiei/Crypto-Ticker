package com.akkiei.cryptoticker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static android.content.ContentValues.TAG;

public class PostNotifications {
    Context mContext;

    public PostNotifications(Context context) {
        mContext = context;
    }

    public void createNotification(String title, String text, String channelId) {
        Log.d(TAG, "createNotification: Notification posted");
        Intent intent = new Intent(mContext, CreateTriggers.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, channelId)
                .setSmallIcon(R.drawable.ic_launcher_my_icon_background)
                .setContentTitle(title)
                .setContentIntent(pendingIntent)
                .setContentText(text)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Click to know more"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = title;
            String description = "this is dummy notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);
        notificationManager.notify(Integer.parseInt(channelId), builder.build());
    }
}
