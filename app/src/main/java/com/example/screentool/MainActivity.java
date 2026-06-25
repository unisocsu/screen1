package com.example.screentool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import java.io.OutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, NotificationService.class));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(this);
        btn.setText("צלם מסך וצא אוטומטית");
        
        btn.setOnClickListener(v -> takeScreenshotAndMinimize());

        layout.addView(btn);
        setContentView(layout);
    }

    private void takeScreenshotAndMinimize() {
        // 1. חזרה למסך הבית באופן אוטומטי
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        Toast.makeText(this, "מצלם ברקע...", Toast.LENGTH_SHORT).show();

        // 2. צילום אחרי השהיה קצרה (כדי לתת למערכת זמן להתרנדר)
        new Thread(() -> {
            try {
                Thread.sleep(1000); // שניה אחת מספיקה כי יצאנו מהאפליקציה

                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 
                              + "/screenshot_" + System.currentTimeMillis() + ".png";
                
                Process process = Runtime.getRuntime().exec("su");
                OutputStream os = process.getOutputStream();
                os.write(("/system/bin/screencap -p " + path + "\n").getBytes());
                os.write("exit\n".getBytes());
                os.flush();
                os.close();
                process.waitFor();

                runOnUiThread(() -> Toast.makeText(this, "המסך צולם ונשמר!", Toast.LENGTH_LONG).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(this, "שגיאה! וודא שיש ROOT", Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}
