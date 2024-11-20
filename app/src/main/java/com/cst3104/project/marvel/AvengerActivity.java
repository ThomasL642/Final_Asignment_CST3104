package com.cst3104.project.marvel;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.cst3104.project.R;
import java.util.ArrayList;
import java.util.Random;

public class AvengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avenger);

        // Data source
        ArrayList<Marvel> avengers = Marvel.readData(this);

        //Declare Varaibles
        int numberOfChoices = 5;
        // Get ImageView
        ImageView WinningAvengerView = findViewById(R.id.currentAvengerView);
        ListView CurrentChoicesView = findViewById(R.id.currentChoices);

        
        // Pick a random character and Display
        Marvel WinningAvenger = getRandomMarvel(avengers);
        DisplayAvenger(WinningAvenger, WinningAvengerView);

        //create array of avenger for listview
        // add current avenger to listview
        ArrayList<Marvel> CurrentAvengers = getAvengerChoices(WinningAvenger, avengers, numberOfChoices);

        //Add the Choices to the list view
        // Set the custom adapter to the ListView
        ChoicesAdapter ChoicesAdapter = new ChoicesAdapter(this, CurrentAvengers);
        CurrentChoicesView.setAdapter(ChoicesAdapter);

        ChoicesAdapter.notifyDataSetChanged();
        
    }


    //Prams: winningAvenger, avengers, numberOfChoices
    //Make a list of avengers for the player to chose from
    private ArrayList<Marvel> getAvengerChoices(Marvel winningAvenge, ArrayList<Marvel> avengers, int numberOfChoices) {
        ArrayList<Marvel> CurrentAvengers = new ArrayList<>();
        CurrentAvengers.add(winningAvenge);
        while (CurrentAvengers.size() < (numberOfChoices)) {
            Marvel randomAvenger = getRandomMarvel(avengers);

            // Check for duplicates
            if (!CurrentAvengers.contains(randomAvenger)) {
                CurrentAvengers.add(randomAvenger);
            }
        }
        return CurrentAvengers;
    }

    // Method to get a random Marvel character and display it
    private Marvel getRandomMarvel(ArrayList<Marvel> avengers) {
        Random random = new Random();
        int randomIndex = random.nextInt(avengers.size());
        return avengers.get(randomIndex);
    }

    private void DisplayAvenger(Marvel avenger, ImageView avengersImgView) {
        avenger.flagInImageView(avengersImgView);
    }
}
