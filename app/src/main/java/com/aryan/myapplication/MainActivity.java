package com.aryan.myapplication;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aryan.yt.engine.AryanYT;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ARYAN_YT";
    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        responseTextView = findViewById(R.id.responseTextView);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Initialize AryanYT Library
        AryanYT.init();

        // 2. Set Click Listener to Copy
        responseTextView.setOnClickListener(v -> {
            String text = responseTextView.getText().toString();
            if (!text.isEmpty()) {
                copyToClipboard(text);
            }
        });

        // 3. Extract Data directly as JSON
        new Thread(() -> {
            try {
                String videoUrl = "https://www.youtube.com/live/Nq2wYlWFucg?si=PX5ZwGWW6Uls6I8p";
                Log.d(TAG, "Extracting JSON for: " + videoUrl);
                
                final String jsonResult = AryanYT.extractJson(videoUrl);
                
                // Update UI on correct thread
                runOnUiThread(() -> {
                    responseTextView.setText(jsonResult);
                });

                Log.d(TAG, "--- JSON RESULT ---");
                Log.d(TAG, jsonResult);
                Log.d(TAG, "-------------------");

            } catch (Exception e) {
                Log.e(TAG, "Extraction Error: ", e);
                runOnUiThread(() -> {
                    responseTextView.setText("Error: " + e.getMessage());
                });
            }
        }).start();
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("AryanYT_JSON", text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(this, "JSON Copied to Clipboard!", Toast.LENGTH_SHORT).show();
        }
    }
}