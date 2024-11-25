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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cst3104.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The DashboardActivity class provides an interface for users to view their game performance.
 * It displays the latest, lowest, and highest scores and a list of all recorded scores.
 * It also allows users to start a new game through the Play button.
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
     * RecyclerView for displaying the list of all game scores.
     */
    private RecyclerView recyclerView;

    /**
     * Adapter for populating the RecyclerView with game scores.
     */
    private ScoreAdapter scoreAdapter;

    /**
     * Button to start a new game.
     */
    private Button playButton;

    /**
     * Username passed from the LoginActivity.
     */
    private String username;

    /**
     * ViewModel for managing and observing game statistics and scores.
     */
    private DashboardViewModel dashboardViewModel;

    /**
     * Called when the activity is created.
     * Sets up the toolbar, initializes views, and sets up the ViewModel and RecyclerView.
     * Observes LiveData to display the latest, lowest, highest, and all recorded scores.
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
        recyclerView = findViewById(R.id.recyclerViewScores);
        playButton = findViewById(R.id.buttonPlay);

        // Get username from intent
        username = getIntent().getStringExtra("username");

        // Set up RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        scoreAdapter = new ScoreAdapter(new ArrayList<>()); // Initialize with an empty list
        recyclerView.setAdapter(scoreAdapter);

        // Initialize the ViewModel
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Observe LiveData for latest, lowest, and highest scores
        dashboardViewModel.getLatestGame().observe(this, latestGame -> {
            latestGameTextView.setText(latestGame != null ? latestGame : getString(R.string.no_data_available));
        });

        dashboardViewModel.getLowestGame().observe(this, lowestGame -> {
            lowestGameTextView.setText(lowestGame != null ? lowestGame : getString(R.string.no_data_available));
        });

        dashboardViewModel.getHighestGame().observe(this, highestGame -> {
            highestGameTextView.setText(highestGame != null ? highestGame : getString(R.string.no_data_available));
        });

        // Observe the list of all scores for the RecyclerView
        dashboardViewModel.getAllScores().observe(this, new Observer<List<UserInfo>>() {
            @Override
            public void onChanged(List<UserInfo> userInfoList) {
                // Update the RecyclerView with the new list
                scoreAdapter.setScores(userInfoList);
            }
        });

        // Load game statistics into the ViewModel
        dashboardViewModel.loadGameStatistics(getApplicationContext());

        // Set up Play button click listener
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AvengerActivity.class);
            intent.putExtra("username", username); // Pass username to AvengerActivity
            startActivity(intent); // Start AvengerActivity
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
     * Displays dialogs with information or help based on the selected menu item.
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
                            + "- The highest score achieved so far.\n"
                            + "- A list of all recorded scores.\n\n"
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
                            + "- View all game scores in the list below.\n"
                            + "- Press the 'Play' button to start a new game.\n"
                            + "- Use the toolbar menu for more options.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
