package com.example.screentool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.OutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startService(new Intent(this, NotificationService.class));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        Button btn = new Button(this);
        btn.setText("צלם מסך וצא");
        
        btn.setOnClickListener(v -> takeScreenshotAndMinimize());

        layout.addView(btn);
        setContentView(layout);
    }

    private void takeScreenshotAndMinimize() {
        // התיקון המרכזי: שולח את האפליקציה הנוכחית לרקע
        // זה מחזיר את המשתמש אוטומטית בדיוק למסך האחרון שבו הוא היה
        moveTaskToBack(true);

        Toast.makeText(getApplicationContext(), "מצלם ברקע...", Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            try {
                Thread.sleep(1000); 

                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 
                              + "/screenshot_" + System.currentTimeMillis() + ".png";
                
                Process process = Runtime.getRuntime().exec("su");
                OutputStream os = process.getOutputStream();
                os.write(("/system/bin/screencap -p " + path + "\n").getBytes());
                os.write("exit\n".getBytes());
                os.flush();
                os.close();
                process.waitFor();

                // שימוש ב-getApplicationContext מבטיח שהטקסט יקפוץ גם כשהאפליקציה ממוזערת
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "המסך צולם!", Toast.LENGTH_SHORT).show());
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(getApplicationContext(), "שגיאה! וודא שיש ROOT", Toast.LENGTH_LONG).show());
            }
        }).start();
    }
}
