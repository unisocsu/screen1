package com.example.screentool;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.File;
import android.os.Environment;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = new LinearLayout(this);
        Button btn = new Button(this);
        btn.setText("צלם מסך");
        
        btn.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // אנדרואיד ישן: נשתמש ב-ROOT
                takeScreenshotWithRoot();
            } else {
                // אנדרואיד חדש: כאן תממש בעתיד את ה-MediaProjection
                Toast.makeText(this, "לגרסאות חדשות נדרש MediaProjection", Toast.LENGTH_LONG).show();
            }
        });

        layout.addView(btn);
        setContentView(layout);
    }

    private void takeScreenshotWithRoot() {
        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 
                          + "/screenshot_" + System.currentTimeMillis() + ".png";
            Process sh = Runtime.getRuntime().exec("su", null, null);
            java.io.OutputStream os = sh.getOutputStream();
            os.write(("/system/bin/screencap -p " + path + "\n").getBytes());
            os.flush();
            os.close();
            sh.waitFor();
            Toast.makeText(this, "צולם בהצלחה!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "שגיאה: אין הרשאות ROOT", Toast.LENGTH_LONG).show();
        }
    }
}
