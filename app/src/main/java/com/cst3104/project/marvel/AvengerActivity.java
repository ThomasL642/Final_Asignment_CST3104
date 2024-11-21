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

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.cst3104.project.R;
import java.util.ArrayList;
import java.util.Random;

public class AvengerActivity extends AppCompatActivity {
    //Declare Variables
    private Toolbar toolbar;
    private ImageView WinningAvengerView;
    private TextView counterView;
    private ListView CurrentChoicesView;
    private TextView winningAvengerName;
    private int counter;
    private int numberOfChoices = 7;
    private boolean rightAnswerChosen = false;
    private Marvel WinningAvenger;
    private ArrayList<Marvel> CurrentAvengers;
    private ArrayList<Marvel> avengers;
    private ChoicesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avenger);

        // Get toolbar
        toolbar = findViewById(R.id.toolbar);

        // Data source
        avengers = Marvel.readData(this);
        //Get winning avenger view
        winningAvengerName = findViewById(R.id.winningAvengerNameView);
        //Get counter view and set it
        counterView = findViewById(R.id.counterView);
        counterView.setText(String.valueOf(counter));
        // Get ImageView
        WinningAvengerView = findViewById(R.id.currentAvengerView);
        CurrentChoicesView = findViewById(R.id.currentChoices);

        
        // Pick a random character and Display
        WinningAvenger = getRandomMarvel();
        DisplayAvenger(WinningAvenger, WinningAvengerView);

        //create array of avenger for listview
        // add current avenger to listview
        CurrentAvengers = getAvengerChoices();

        //Add the Choices to the list view
        // Set the custom adapter to the ListView
        adapter = new ChoicesAdapter(this, CurrentAvengers);
        CurrentChoicesView.setAdapter(adapter);

        CurrentChoicesView.setOnItemClickListener((parent, view, position, id) -> {
            //get avenger name and check
            //if its right
            if (!rightAnswerChosen) {
                if (CurrentAvengers.get(position).toString().equals(WinningAvenger.toString())) {
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_Green));
                    rightAnswerChosen = true;
                    winningAvengerName.setText(WinningAvenger.toString());
                }
                //else (wrong avenger)
                else {
                    counter += 1;
                    counterView.setText(String.valueOf(counter));
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_Red));
                }
            }
        });
    }

    //resets game
    private void resetGame () {
        //reset counter
        counter = 0;
        counterView.setText(String.valueOf(counter));
        counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        //new Game!
        rightAnswerChosen = false;
        //Empty answer textview
        winningAvengerName.setText(getString(R.string.empty));
        //pick new avenger
        WinningAvenger = getRandomMarvel();
        DisplayAvenger(WinningAvenger, WinningAvengerView);
        //Get new list og avengers
        updateAvengerChoices();
        adapter.notifyDataSetChanged();
    }

    // Script for overflow menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if ( id ==  R.id.resetGame) {
            resetGame();
        }

        return super.onOptionsItemSelected(item);
    }

    // gets toolbar layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    // Modify the existing array instead of creating a new one
    private void updateAvengerChoices() {
        // Clear the existing list
        CurrentAvengers.clear();

        // Add the WinningAvenger to the list
        CurrentAvengers.add(WinningAvenger);

        // Fill up the list to the desired number of choices
        while (CurrentAvengers.size() < numberOfChoices) {
            Marvel randomAvenger = getRandomMarvel();

            // Check for duplicates
            if (!CurrentAvengers.contains(randomAvenger)) {
                CurrentAvengers.add(randomAvenger);
            }
        }

        // Notify the adapter to refresh the list view
        adapter.notifyDataSetChanged();
    }

    //Make a list of avengers for the player to chose from
    private ArrayList<Marvel> getAvengerChoices() {
        ArrayList<Marvel> newAvengers = new ArrayList<>();
        newAvengers.add(WinningAvenger);
        while (newAvengers.size() < (numberOfChoices)) {
            Marvel randomAvenger = getRandomMarvel();

            // Check for duplicates
            if (!newAvengers.contains(randomAvenger)) {
                newAvengers.add(randomAvenger);
            }
        }
        return newAvengers;
    }

    // Method to get a random Marvel character and display it
    private Marvel getRandomMarvel() {
        Random random = new Random();
        int randomIndex = random.nextInt(avengers.size());
        return avengers.get(randomIndex);
    }

    private void DisplayAvenger(Marvel avenger, ImageView avengersImgView) {
        avenger.flagInImageView(avengersImgView);
    }
}
