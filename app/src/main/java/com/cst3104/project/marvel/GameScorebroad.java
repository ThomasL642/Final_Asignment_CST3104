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
 * Date : November 24 2024
 */

package com.cst3104.project.marvel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import com.cst3104.project.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class GameScorebroad extends AppCompatActivity {
    private Toolbar toolbar;
    private ScoreDatabase db;
    private ScoreDAO mDAO;
    private ArrayList<UserInfo> UsersInfo;
    private ListView scorebroadListView;
    private UserInfoAdapter userAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_scorebroad);

        UsersInfo = new ArrayList<>();

        // Get toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));

        scorebroadListView = findViewById(R.id.scorebroadList);
        userAdapter = new UserInfoAdapter(this, UsersInfo);
        scorebroadListView.setAdapter(userAdapter);

        // Initialize the database
        db = Room.databaseBuilder(getApplicationContext(),
                ScoreDatabase.class, getString(R.string.databaseName)).build();
        mDAO = db.scoreDAO();

        loadScoresFromDatabase();

    }

    // Script for info menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if ( id ==  R.id.info_icon) {
            showInfoAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    // gets toolbar layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_scorebroad_toolbar, menu);
        return true;
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.infoTitleAdmin))
                .setMessage(getString(R.string.infoAdmin))
                .show();
    }

    private void loadScoresFromDatabase() {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            try {
                List<UserInfo> oldUsersInfo = mDAO.getAllUserInfo();

                if (oldUsersInfo != null && !oldUsersInfo.isEmpty()) {

                    // Clear existing userinfo to avoid duplicates
                    UsersInfo.clear();

                    // Populate messages and chatMessages lists
                    for (UserInfo userInfo : oldUsersInfo) {
                        UsersInfo.add(userInfo);
                    }

                    // Update the adapter on the main thread after loading all messages
                    runOnUiThread(() -> userAdapter.notifyDataSetChanged());
                }
            } catch (Exception e) {
                // Log the exception or handle it to prevent crashing
                Log.e(getString(R.string.databaseError), getString(R.string.errorMessage), e);
            }
        });
    }
}
