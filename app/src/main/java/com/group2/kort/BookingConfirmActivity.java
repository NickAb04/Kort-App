package com.group2.kort;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.Calendar; // Required for time scheduling

public class BookingConfirmActivity extends AppCompatActivity {
    DataHelper dbHelper;
    String sport, court, date, time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        dbHelper = new DataHelper(this);

        // Retrieve booking data from Intent
        sport = getIntent().getStringExtra("SPORT");
        court = getIntent().getStringExtra("COURT");
        date = getIntent().getStringExtra("DATE");
        time = getIntent().getStringExtra("TIME");

        // Display Summary
        ((TextView)findViewById(R.id.tvSummarySport)).setText("Sport: " + sport);
        ((TextView)findViewById(R.id.tvSummaryCourt)).setText("Court: " + court);
        ((TextView)findViewById(R.id.tvSummaryDateTime)).setText(date + " | " + time);

        // Step 1: Request notification permission for Android 13+ (Requirement for physical devices)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        findViewById(R.id.btnFinalConfirm).setOnClickListener(v -> {
            saveToOfflineDB();      // Lab 3: Offline Database logic [cite: 959]
            setAlarm(sport);        // Lab 6: Alarm Notification logic [cite: 297]

            // Redirect to Dashboard
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
    }

    private void saveToOfflineDB() {
        // Lab 3 Technique: Using execSQL to insert data into local SQLite [cite: 1195, 1863]
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "insert into bookings(sport, court, date, time) values('" +
                sport + "','" + court + "','" + date + "','" + time + "')";
        db.execSQL(sql);
        Toast.makeText(this, "Booking saved to History!", Toast.LENGTH_SHORT).show();
    }

    private void setAlarm(String sportName) {
        // Lab 6 Technique: Use AlarmManager to perform operations outside app lifetime [cite: 304, 305]
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create Intent to trigger the AlarmReceiver class [cite: 642, 761]
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("msg", "KORT Reminder: Your " + sportName + " court is ready!");

        // PendingIntent allows the system to execute the intent later [cite: 300, 301]
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,
                intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Set the alarm to trigger 5 seconds after confirmation for testing [cite: 777, 781]
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        // Schedule the alarm [cite: 784]
        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
        }
    }
}