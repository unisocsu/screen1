package com.example.screentool;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // יצירת ממשק בסיסי ללא XML
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 
                ViewGroup.LayoutParams.MATCH_PARENT));

        Button btn = new Button(this);
        btn.setText("הפעל כלי צילום מסך");
        
        btn.setOnClickListener(v -> {
            // התחלת השירות בדרך שתואמת גם ל-Android 4.4
            Intent intent = new Intent(MainActivity.this, NotificationService.class);
            startService(intent);
            Toast.makeText(MainActivity.this, "השירות הופעל ברקע", Toast.LENGTH_SHORT).show();
        });

        layout.addView(btn);
        setContentView(layout);
    }
}
