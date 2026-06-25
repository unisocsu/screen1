package com.example.screentool;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class NotificationService extends Service {
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        Notification.Builder builder = new Notification.Builder(this)
                .setContentTitle("כלי צילום מסך")
                .setContentText("השירות פועל")
                .setSmallIcon(android.R.drawable.stat_sys_download_done);
        startForeground(9999, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}
