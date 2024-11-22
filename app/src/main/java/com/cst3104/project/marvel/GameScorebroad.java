/**
 * Full Name: Thomas Lawrence
 *
 * Student ID: 041120273
 *
 * Course: CST3104
 *
 * Term: Fall 2024
 *
 * Assignment: Team Project
 *
 * Date: November 24 2024
 */

package com.cst3104.project.marvel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.cst3104.project.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The GameScorebroad activity displays a leaderboard of user scores fetched from a Room database.
 */
public class GameScorebroad extends AppCompatActivity {

    /**
     * Toolbar for app navigation and menu.
     */
    private Toolbar toolbar;

    /**
     * Database for storing and retrieving user scores.
     */
    private ScoreDatabase db;

    /**
     * Data access object (DAO) for interacting with the ScoreDatabase.
     */
    private ScoreDAO mDAO;

    /**
     * List of UserInfo objects representing user scores.
     */
    private ArrayList<UserInfo> UsersInfo;

    /**
     * ListView for displaying the scores.
     */
    private ListView scorebroadListView;

    /**
     * Adapter for managing and displaying UserInfo data in the ListView.
     */
    private UserInfoAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_scorebroad);

        // Initialize the list of user scores
        UsersInfo = new ArrayList<>();

        // Set up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));

        // Set up the ListView and its adapter
        scorebroadListView = findViewById(R.id.scorebroadList);
        userAdapter = new UserInfoAdapter(this, UsersInfo);
        scorebroadListView.setAdapter(userAdapter);

        // Initialize the database and its DAO
        db = Room.databaseBuilder(
                getApplicationContext(),
                ScoreDatabase.class,
                getString(R.string.databaseName)
        ).build();
        mDAO = db.scoreDAO();

        // Load scores from the database
        loadScoresFromDatabase();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        // Show an info alert if the info menu item is clicked
        if (id == R.id.info_icon) {
            showInfoAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for the toolbar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_scorebroad_toolbar, menu);
        return true;
    }

    /**
     * Displays an informational alert dialog about the GameScorebroad activity.
     */
    private void showInfoAlert() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.infoTitleAdmin))
                .setMessage(getString(R.string.infoAdmin))
                .show();
    }

    /**
     * Loads scores from the database asynchronously and updates the ListView on the main thread.
     */
    private void loadScoresFromDatabase() {
        // Use a background thread for database operations
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                // Fetch all UserInfo records from the database
                List<UserInfo> oldUsersInfo = mDAO.getAllUserInfo();

                // Check if any records exist in the database
                if (oldUsersInfo != null && !oldUsersInfo.isEmpty()) {
                    // Clear the current list to prevent duplicate entries
                    UsersInfo.clear();

                    // Add all fetched records to the UsersInfo list
                    UsersInfo.addAll(oldUsersInfo);

                    // Update the adapter on the main thread to reflect changes
                    runOnUiThread(() -> userAdapter.notifyDataSetChanged());
                }
            } catch (Exception e) {
                // Log the exception and handle errors to prevent app crashes
                Log.e(getString(R.string.databaseError), getString(R.string.errorMessage), e);
            }
        });
    }
}
