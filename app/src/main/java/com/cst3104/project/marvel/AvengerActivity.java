package com.cst3104.project.marvel;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.cst3104.project.R;
import java.util.ArrayList;

public class AvengerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_avenger);

        // Data source
        ArrayList<Marvel> avengers = Marvel.readData(this);


    }
}
