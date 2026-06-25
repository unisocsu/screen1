package com.example.screentool;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // שימוש בסמל סטנדרטי שעובד בכל גרסה
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("כלי צילום מסך פעיל")
                .setContentText("לחץ כדי לפתוח את האפליקציה")
                .setSmallIcon(android.R.drawable.ic_menu_camera) 
                .setContentIntent(pendingIntent)
                .setOngoing(true);

        startForeground(9999, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
