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

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
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
import androidx.room.Room;

import com.cst3104.project.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * The AvengerActivity class is the main activity for the Avengers game.
 * It provides the functionality to display and interact with a game
 * where players guess the correct Avenger based on the choices provided.
 */
public class AvengerActivity extends AppCompatActivity {

    /**
     * Toolbar for displaying the app's menu.
     */
    private Toolbar toolbar;

    /**
     * ImageView to display the current winning Avenger.
     */
    private ImageView WinningAvengerView;

    /**
     * TextView to display the player's score counter.
     */
    private TextView counterView;

    /**
     * RecyclerView to display the list of current Avenger choices.
     */
    private RecyclerView CurrentChoicesView;

    /**
     * ViewModel to handle game state and logic.
     */
    private GameViewModel viewModel;

    /**
     * TextView to display the name of the winning Avenger.
     */
    private TextView winningAvengerName;

    /**
     * Number of Avenger choices presented to the player.
     */
    private int numberOfChoices = 7;

    /**
     * List of all available Avengers.
     */
    private ArrayList<Marvel> avengers;

    /**
     * Adapter for managing the RecyclerView of Avenger choices.
     */
    private ChoicesAdapter adapter;

    /**
     * Database for storing and retrieving user scores.
     */
    private ScoreDatabase db;

    /**
     * Data access object (DAO) for interacting with the ScoreDatabase.
     */
    private ScoreDAO mDAO;

    /**
     * String representing the users name
     */
    private String userName = "Kendrick Lamar";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avenger);

        // Initialize the database and its DAO
        db = Room.databaseBuilder(
                getApplicationContext(),
                ScoreDatabase.class,
                getString(R.string.databaseName)
        ).build();
        mDAO = db.scoreDAO();


        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(GameViewModel.class);

        // Set up RecyclerView for Avenger choices
        CurrentChoicesView = findViewById(R.id.currentChoices);
        CurrentChoicesView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Load data and initialize views
        avengers = Marvel.readData(this);
        winningAvengerName = findViewById(R.id.winningAvengerNameView);
        counterView = findViewById(R.id.counterView);
        counterView.setText(String.valueOf(viewModel.counter));
        WinningAvengerView = findViewById(R.id.currentAvengerView);

        // Set up game state if not already initialized
        if (viewModel.CurrentAvengers.isEmpty()) {
            viewModel.WinningAvenger = viewModel.getRandomMarvel(avengers);
            viewModel.updateAvengerChoices(avengers, numberOfChoices);
        }

        counterView.setText(String.valueOf(viewModel.counter));
        DisplayAvenger(viewModel.WinningAvenger, WinningAvengerView);

        // Initialize adapter and set it to the RecyclerView
        adapter = new ChoicesAdapter(this, viewModel.CurrentAvengers, position -> {
            if (!viewModel.rightAnswerChosen) {
                if (viewModel.CurrentAvengers.get(position).toString().equals(viewModel.WinningAvenger.toString())) {
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.correct_Green));
                    viewModel.rightAnswerChosen = true;
                    winningAvengerName.setText(viewModel.WinningAvenger.toString());
                    //add score, user name, and time played to database
                    Executor thread = Executors.newSingleThreadExecutor();
                    thread.execute(() -> {
                        UserInfo currentPlayer = new UserInfo(viewModel.counter, userName);
                        mDAO.insertUserInfo(currentPlayer);
                    });
                } else {
                    viewModel.counter++;
                    counterView.setText(String.valueOf(viewModel.counter));
                    counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.wrong_Red));
                }
            }
        });
        CurrentChoicesView.setAdapter(adapter);
    }

    /**
     * Resets the game state to its initial values.
     */
    private void resetGame() {
        viewModel.resetGame(avengers, numberOfChoices);

        counterView.setText(String.valueOf(viewModel.counter));
        counterView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        winningAvengerName.setText(getString(R.string.empty));
        DisplayAvenger(viewModel.WinningAvenger, WinningAvengerView);

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reset_game_icon) {
            resetGame();
        } else if (id == R.id.website_image) {
            String winningAvengerLink = viewModel.WinningAvenger.getUrl();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(winningAvengerLink));
            startActivity(browserIntent);
        } else if (id == R.id.info_icon) {
            showInfoAlert();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.avengers_activity_toolbar, menu);
        return true;
    }

    /**
     * Displays an information alert dialog about the game.
     */
    private void showInfoAlert() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.infoTitleGame))
                .setMessage(getString(R.string.infoGame))
                .show();
    }

    /**
     * Displays the given Avenger in the provided ImageView.
     *
     * @param avenger The Marvel object representing the Avenger.
     * @param avengersImgView The ImageView where the Avenger's image will be displayed.
     */
    private void DisplayAvenger(Marvel avenger, ImageView avengersImgView) {
        avenger.flagInImageView(avengersImgView);
    }
}
