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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.cst3104.project.R;
import java.util.ArrayList;
import java.util.Random;
import androidx.lifecycle.ViewModel;

public class AvengerActivity extends AppCompatActivity {
    //Declare Variables
    private Toolbar toolbar;
    private ImageView WinningAvengerView;
    private TextView counterView;
    private RecyclerView CurrentChoicesView;
    private GameViewModel viewModel;
    private TextView winningAvengerName;
    private int numberOfChoices = 7;
    private ArrayList<Marvel> avengers;
    private ChoicesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avenger);

        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        CurrentChoicesView = findViewById(R.id.currentChoices);
        CurrentChoicesView.setLayoutManager(new LinearLayoutManager(this));

        // Get toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Data source
        avengers = Marvel.readData(this);
        //Get winning avenger view
        winningAvengerName = findViewById(R.id.winningAvengerNameView);
        //Get counter view and set it
        counterView = findViewById(R.id.counterView);
        counterView.setText(String.valueOf(viewModel.counter));
        // Get ImageView
        WinningAvengerView = findViewById(R.id.currentAvengerView);
        CurrentChoicesView = findViewById(R.id.currentChoices);

        if (viewModel.CurrentAvengers.isEmpty()) {
            viewModel.WinningAvenger = viewModel.getRandomMarvel(avengers);
            viewModel.updateAvengerChoices(avengers, numberOfChoices);
        }

        counterView.setText(String.valueOf(viewModel.counter));
        DisplayAvenger(viewModel.WinningAvenger, WinningAvengerView);

        adapter = new ChoicesAdapter(this, viewModel.CurrentAvengers, position -> {
            if (!viewModel.rightAnswerChosen) {
                if (viewModel.CurrentAvengers.get(position).toString().equals(viewModel.WinningAvenger.toString())) {
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_Green));
                    viewModel.rightAnswerChosen = true;
                    winningAvengerName.setText(viewModel.WinningAvenger.toString());
                } else {
                    viewModel.counter++;
                    counterView.setText(String.valueOf(viewModel.counter));
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_Red));
                }
            }
        });
        CurrentChoicesView.setAdapter(adapter);
    }

    //resets game
    private void resetGame () {
        viewModel.resetGame(avengers, numberOfChoices);

        counterView.setText(String.valueOf(viewModel.counter));
        counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        winningAvengerName.setText(getString(R.string.empty));
        DisplayAvenger(viewModel.WinningAvenger, WinningAvengerView);

        adapter.notifyDataSetChanged();
    }

    // Script for toolbar menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if ( id ==  R.id.reset_game_icon) {
            resetGame();
        }
        if ( id == R.id.website_image) {
            String winningAvengerLink = viewModel.WinningAvenger.getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(winningAvengerLink));
            startActivity(browserIntent);
        }
        if ( id == R.id.info_icon) {
            showInfoAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    // gets toolbar layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.avengers_activity_toolbar, menu);
        return true;
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.infoTitleGame))
                .setMessage(getString(R.string.infoGame))
                .show();
    }


    //Make a list of avengers for the player to chose from
    private ArrayList<Marvel> getAvengerChoices() {
        ArrayList<Marvel> newAvengers = new ArrayList<>();
        while (newAvengers.size() < (numberOfChoices-1)) {
            Marvel randomAvenger = viewModel.getRandomMarvel(avengers);

            // Check for duplicates
            if (!newAvengers.contains(randomAvenger)) {
                newAvengers.add(randomAvenger);
            }
        }
        Random randomInsert = new Random();
        int randomIndexInsert = randomInsert.nextInt(numberOfChoices);
        newAvengers.add(randomIndexInsert, viewModel.WinningAvenger);
        return newAvengers;
    }

    private void DisplayAvenger(Marvel avenger, ImageView avengersImgView) {
        avenger.flagInImageView(avengersImgView);
    }
}
