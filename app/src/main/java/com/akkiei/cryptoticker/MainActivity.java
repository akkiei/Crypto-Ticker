package com.akkiei.cryptoticker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.WorkManager;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = this.findViewById(R.id.button);
        Intent foregroundServiceIntent = new Intent(this, ForegroundService.class);
        startForegroundService(foregroundServiceIntent);
    }

    public void postNotificationClicked(View v) {

        if (v.getId() == R.id.button) {
            Intent intent = new Intent(this, CreateTriggers.class);
            startActivity(intent);
        }
    }

    public void listTriggers(View v) {

        if (v.getId() == R.id.button) {
            Intent intent = new Intent(this, CreateTriggers.class);
            startActivity(intent);
        }
    }

    public void stopTriggers(View v) {

        if (v.getId() == R.id.reset_button) {
            Toast.makeText(this,"All Triggers Removed",Toast.LENGTH_SHORT).show();
            WorkManager.getInstance(this).cancelAllWork();
        }
    }


}