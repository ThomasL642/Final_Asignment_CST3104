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
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cst3104.project.R;

/**
 * The LoginActivity class handles user authentication and navigation.
 * Users can enter a username to proceed to different pages based on their input.
 * Includes a help menu for guidance on using the login screen.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * EditText for entering the username.
     */
    private EditText emailEditText;

    /**
     * Button for submitting the username and navigating to the appropriate screen.
     */
    private Button loginButton;

    /**
     * Called when the activity is created.
     * Sets up the toolbar, initializes views, and sets the login button's functionality.
     *
     * @param savedInstanceState State information passed from the system.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize Views
        emailEditText = findViewById(R.id.editTextTextEmailAddress2);
        loginButton = findViewById(R.id.button);

        // Set Login Button Click Listener
        loginButton.setOnClickListener(v -> {
            String username = emailEditText.getText().toString().trim();

            // Check for empty username or less than 3 characters
            if (username.isEmpty() || username.length() < 3) {
                Toast.makeText(LoginActivity.this, "Username must be at least 3 characters long", Toast.LENGTH_SHORT).show();
            } else if (username.equalsIgnoreCase("admin")) {
                // Navigate to the GameScoreboard screen
                Intent intent = new Intent(LoginActivity.this, GameScorebroad.class);
                startActivity(intent);
                finish();
            } else {
                // Navigate to the Dashboard screen and pass username
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                intent.putExtra("username", username); // Pass username to Dashboard
                startActivity(intent);
                finish();
            }
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
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    /**
     * Handles menu item clicks.
     * Provides an info dialog for app details and a help dialog for login instructions.
     *
     * @param item The selected menu item.
     * @return True if the menu action is successfully handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_info) {
            // Show Info Dialog
            new AlertDialog.Builder(this)
                    .setTitle("Application Info")
                    .setMessage("This application is created for the CST3104 course.\n"
                            + "Authors: Hamza Habiballah and Thomas Lawrence.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        } else if (item.getItemId() == R.id.action_help) {
            // Show Help Dialog
            new AlertDialog.Builder(this)
                    .setTitle("Login Screen Help")
                    .setMessage("To use the Login Screen:\n\n"
                            + "- Enter a username in the text field.\n"
                            + "- If you type 'admin', you will navigate to the Scoreboard page.\n"
                            + "- For any other username, you will be taken to the Dashboard page.\n"
                            + "- The username must be at least 3 characters long.")
                    .setPositiveButton("OK", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
