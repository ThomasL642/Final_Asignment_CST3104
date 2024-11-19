package com.cst3104.project.marvel;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

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

        ImageView imageView = findViewById(R.id.currentAvengerView);
        Log.d("AvengersSize", "Size: " + avengers.size());
        // Pick a random character
        if (!avengers.isEmpty()) {
            Marvel randomCharacter = getRandomMarvel(avengers);

            // Display the random character's image in the ImageView
            randomCharacter.flagInImageView(imageView);
            Log.d("ImageResource", "ID: " + randomCharacter.getDrawableId());

        }
    }

    // Method to get a random Marvel character from the list
    private Marvel getRandomMarvel(ArrayList<Marvel> avengers) {
        Random random = new Random();
        int randomIndex = random.nextInt(avengers.size());
        return avengers.get(randomIndex);
    }
}
