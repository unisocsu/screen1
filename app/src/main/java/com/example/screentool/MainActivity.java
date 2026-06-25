package com.example.screentool;

import android.app.Activity;
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

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        Button btn = new Button(this);
        btn.setText("צלם מסך (ROOT)");
        
        btn.setOnClickListener(v -> takeScreenshotWithRoot());

        layout.addView(btn);
        setContentView(layout);
    }

    private void takeScreenshotWithRoot() {
        try {
            // הגדרת נתיב לשמירת התמונה
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 
                          + "/screenshot_" + System.currentTimeMillis() + ".png";
            
            // הרצת פקודת הצילום של אנדרואיד דרך ROOT
            Process process = Runtime.getRuntime().exec("su");
            OutputStream os = process.getOutputStream();
            os.write(("/system/bin/screencap -p " + path + "\n").getBytes());
            os.write("exit\n".getBytes());
            os.flush();
            os.close();
            
            process.waitFor();
            Toast.makeText(this, "המסך צולם ונשמר בגלריה", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "שגיאה: דרוש ROOT!", Toast.LENGTH_LONG).show();
        }
    }
}
