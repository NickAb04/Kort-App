package com.group2.kort;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        findViewById(R.id.btnHistory).setOnClickListener(v ->
                startActivity(new Intent(this, HistoryActivity.class)));

        findViewById(R.id.cardPickleball).setOnClickListener(v -> openSelection("Pickleball"));
        findViewById(R.id.cardBadminton).setOnClickListener(v -> openSelection("Badminton"));
        findViewById(R.id.cardFutsal).setOnClickListener(v -> openSelection("Futsal"));
        findViewById(R.id.cardTennis).setOnClickListener(v -> openSelection("Tennis"));
    }

    private void openSelection(String sport) {
        Intent intent = new Intent(this, CourtSelectionActivity.class);
        intent.putExtra("SPORT_TYPE", sport);
        startActivity(intent);
    }
}