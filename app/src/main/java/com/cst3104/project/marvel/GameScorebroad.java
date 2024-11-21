package com.cst3104.project.marvel;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cst3104.project.R;

public class GameScorebroad extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_scorebroad);

        // Get toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getString(R.string.toolbar_title));


    }

    // gets toolbar layout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_scorebroad_toolbar, menu);
        return true;
    }
}
