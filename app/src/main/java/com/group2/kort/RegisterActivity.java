package com.group2.kort;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // 1. Find the Register Button
        Button btnRegister = findViewById(R.id.btnRegister);

        // 2. Set the Click Listener for the Register Button
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This shows a pop-up to prove the button works!
                Toast.makeText(RegisterActivity.this, "Register Button Pressed!", Toast.LENGTH_LONG).show();
            }
        });

        // 3. Find the "Login" link to go back
        TextView tvLoginLink = findViewById(R.id.tvLoginLink);
        tvLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Goes back to Login screen
            }
        });
    }
}