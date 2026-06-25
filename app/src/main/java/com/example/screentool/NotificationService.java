package com.example.screentool;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        
        // הגדרת Intent שפותח את ה-MainActivity ומביא אותה לחזית
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("כלי צילום מסך פעיל")
                .setContentText("לחץ כדי לפתוח את האפליקציה")
                .setSmallIcon(android.R.drawable.stat_sys_camera)
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        startForeground(9999, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
