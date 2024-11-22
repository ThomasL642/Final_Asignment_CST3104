package com.cst3104.project.marvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cst3104.project.R;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Views
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        loginButton = findViewById(R.id.button);

        // Set Login Button Click Listener
        loginButton.setOnClickListener(v -> {
            String username = emailEditText.getText().toString().trim();

            // Check for empty username or less than 3 characters
            if (username.isEmpty() || username.length() < 3) {
                Toast.makeText(LoginActivity.this, "Username must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            } else if (username.equalsIgnoreCase("admin")) {
                // Navigate to the Scoreboard screen
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Navigate to the Dashboard screen
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            // Show Info Dialog
            new AlertDialog.Builder(this)
                    .setTitle("Application Info")
                    .setMessage("This application is created for the CST3104 course. Authors: Hamza Habiballah and Thomas Lawrence.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
