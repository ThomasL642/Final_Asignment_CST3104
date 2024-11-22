package com.cst3104.project.marvel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.cst3104.project.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DashboardActivity extends AppCompatActivity {

    private TextView latestGameTextView, lowestGameTextView, highestGameTextView, welcomeTextView;
    private Button playButton;
    private ScoreDAO mDAO;
    private ScoreDatabase db;
    private String username; // Hold the username passed from LoginActivity

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


        // Retrieve username from the Intent
        username = getIntent().getStringExtra("username");
        if (username != null) {
            welcomeTextView.setText("Welcome, " + username + "!");
        }

        // Initialize Database
        db = Room.databaseBuilder(
                getApplicationContext(),
                ScoreDatabase.class,
                getString(R.string.databaseName)
        ).build();
        mDAO = db.scoreDAO();

        // Retrieve and display data from the database
        loadGameStatistics();

        // Set Play button click listener
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AvengerActivity.class);
            intent.putExtra("username", username); // Pass username to AvengerActivity
            startActivity(intent);
        });
    }

    private void loadGameStatistics() {
        // Fetch latest game
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            UserInfo latestUserInfo = db.scoreDAO().getLatestUserInfo();
            if (latestUserInfo != null) {
                runOnUiThread(() -> latestGameTextView.setText(getString(
                        R.string.latest_game,
                        latestUserInfo.timestamp,
                        latestUserInfo.score
                )));
            } else {
                runOnUiThread(() -> latestGameTextView.setText(R.string.no_data_available));
            }

            // Fetch lowest game
            UserInfo lowestUserInfo = db.scoreDAO().getLowestUserInfo();
            if (lowestUserInfo != null) {
                runOnUiThread(() -> lowestGameTextView.setText(getString(
                        R.string.lowest_game,
                        lowestUserInfo.timestamp,
                        lowestUserInfo.score
                )));
            } else {
                runOnUiThread(() -> lowestGameTextView.setText(R.string.no_data_available));
            }

            // Fetch highest game
            UserInfo highestUserInfo = db.scoreDAO().getHighestUserInfo();
            if (highestUserInfo != null) {
                runOnUiThread(() -> highestGameTextView.setText(getString(
                        R.string.highest_game,
                        highestUserInfo.timestamp,
                        highestUserInfo.score
                )));
            } else {
                runOnUiThread(() -> highestGameTextView.setText(R.string.no_data_available));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close(); // Close the database when the activity is destroyed
    }
}
