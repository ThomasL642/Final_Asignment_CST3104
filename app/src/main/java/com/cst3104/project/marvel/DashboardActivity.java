/**
 * Full Name: Hamza Habiballah
 *
 * Student ID: 041120464
 *
 * Course: CST3104
 *
 * Term: Fall 2024
 *
 * Assignment: Team Project
 *
 * Date: November 24, 2024
 */

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
import androidx.room.Room;

import com.cst3104.project.R;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The DashboardActivity class provides an interface for users to view their game performance.
 * It displays the latest, lowest, and highest scores and allows users to navigate to the game screen.
 */
public class DashboardActivity extends AppCompatActivity {

    /**
     * TextView for displaying the latest game score and timestamp.
     */
    private TextView latestGameTextView;

    /**
     * TextView for displaying the lowest score and timestamp.
     */
    private TextView lowestGameTextView;

    /**
     * TextView for displaying the highest score and timestamp.
     */
    private TextView highestGameTextView;

    /**
     * Button to start a new game.
     */
    private Button playButton;

    /**
     * Data Access Object (DAO) for performing database operations.
     */
    private ScoreDAO mDAO;

    /**
     * Room database for storing and retrieving user scores.
     */
    private ScoreDatabase db;

    /**
     * Username passed from the LoginActivity.
     */
    private String username;

    /**
     * Called when the activity is created.
     * Sets up the toolbar, initializes the database and views, and fetches score data.
     *
     * @param savedInstanceState State information passed from the system.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Set up the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize views
        latestGameTextView = findViewById(R.id.textViewLatestGame);
        lowestGameTextView = findViewById(R.id.textViewLowestGame);
        highestGameTextView = findViewById(R.id.textViewHighestGame);
        playButton = findViewById(R.id.buttonPlay);

        // Get username from intent
        username = getIntent().getStringExtra("username");

        // Initialize the database
        db = Room.databaseBuilder(
                getApplicationContext(),
                ScoreDatabase.class,
                getString(R.string.databaseName)
        ).build();
        mDAO = db.scoreDAO();

        // Fetch and display game statistics
        loadGameStatistics();

        // Set up Play button click listener
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AvengerActivity.class);
            intent.putExtra("username", username); // Pass username to AvengerActivity
            startActivity(intent); // Start AvengerActivity
        });
    }

    /**
     * Fetches the latest, lowest, and highest game statistics from the database
     * and updates the respective TextViews.
     */
    private void loadGameStatistics() {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            // Fetch and update the latest game score
            UserInfo latestUserInfo = mDAO.getLatestUserInfo();
            runOnUiThread(() -> {
                if (latestUserInfo != null) {
                    latestGameTextView.setText(getString(
                            R.string.latest_game,
                            latestUserInfo.timestamp,
                            latestUserInfo.score
                    ));
                } else {
                    latestGameTextView.setText(R.string.no_data_available);
                }
            });

            // Fetch and update the lowest game score
            UserInfo lowestUserInfo = mDAO.getLowestUserInfo();
            runOnUiThread(() -> {
                if (lowestUserInfo != null) {
                    lowestGameTextView.setText(getString(
                            R.string.lowest_game,
                            lowestUserInfo.timestamp,
                            lowestUserInfo.score
                    ));
                } else {
                    lowestGameTextView.setText(R.string.no_data_available);
                }
            });

            // Fetch and update the highest game score
            UserInfo highestUserInfo = mDAO.getHighestUserInfo();
            runOnUiThread(() -> {
                if (highestUserInfo != null) {
                    highestGameTextView.setText(getString(
                            R.string.highest_game,
                            highestUserInfo.timestamp,
                            highestUserInfo.score
                    ));
                } else {
                    highestGameTextView.setText(R.string.no_data_available);
                }
            });
        });
    }

    /**
     * Inflates the menu options into the toolbar.
     *
     * @param menu The menu to inflate.
     * @return True if the menu is successfully created.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard_info, menu); // Inflate the menu
        return true;
    }

    /**
     * Handles menu item clicks.
     *
     * @param item The selected menu item.
     * @return True if the menu action is successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            // Display dashboard info
            new AlertDialog.Builder(this)
                    .setTitle("Dashboard Info")
                    .setMessage("This page displays:\n\n"
                            + "- The latest game score with its timestamp.\n"
                            + "- The lowest score achieved so far.\n"
                            + "- The highest score achieved so far.\n\n"
                            + "Use this dashboard to track your performance and start a new game!\n\n"
                            + "Authors: Hamza Habiballah and Thomas Lawrence.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            // Display help dialog
            new AlertDialog.Builder(this)
                    .setTitle("Dashboard Help")
                    .setMessage("How to use the Dashboard:\n\n"
                            + "- View your latest, lowest, and highest scores on this page.\n"
                            + "- Press the 'Play' button to start a new game.\n"
                            + "- Use the toolbar menu for more options.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Closes the database when the activity is destroyed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
