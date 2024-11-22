package com.cst3104.project.marvel;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cst3104.project.R;

public class DashboardActivity extends AppCompatActivity {

    private TextView latestGameTextView, lowestGameTextView, highestGameTextView;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Views
        latestGameTextView = findViewById(R.id.textViewLatestGame);
        lowestGameTextView = findViewById(R.id.textViewLowestGame);
        highestGameTextView = findViewById(R.id.textViewHighestGame);
        playButton = findViewById(R.id.buttonPlay);

        // Retrieve and display data (replace placeholders with database values)
        latestGameTextView.setText(getString(R.string.latest_game, "2024/10/23 12:10", 6)); // Replace with database value
        lowestGameTextView.setText(getString(R.string.lowest_game, "2024/10/22 23:10", 1)); // Replace with database value
        highestGameTextView.setText(getString(R.string.highest_game, "2024/10/23 07:10", 39)); // Replace with database value

        // Set Play button click listener
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AvengerActivity.class);
            startActivity(intent);
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
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about_app_title)
                    .setMessage(getString(R.string.about_app_message, "Your Name", "Teammate's Name"))
                    .setPositiveButton(R.string.ok_button, null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
