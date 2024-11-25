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

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.cst3104.project.R;

import java.util.List;

/**
 * The DashboardViewModel class serves as the ViewModel for the DashboardActivity.
 * It handles data retrieval from the database and provides game statistics to the UI.
 */
public class DashboardViewModel extends ViewModel {

    /**
     * LiveData object holding the latest game details.
     */
    private final MutableLiveData<String> latestGame = new MutableLiveData<>();

    /**
     * LiveData object holding the lowest game details.
     */
    private final MutableLiveData<String> lowestGame = new MutableLiveData<>();

    /**
     * LiveData object holding the highest game details.
     */
    private final MutableLiveData<String> highestGame = new MutableLiveData<>();

    /**
     * LiveData object holding all recorded scores for populating the RecyclerView.
     */
    private final MutableLiveData<List<UserInfo>> allScores = new MutableLiveData<>();

    /**
     * Data Access Object (DAO) for interacting with the database.
     */
    private ScoreDAO mDAO;

    /**
     * Retrieves the latest game details as LiveData.
     *
     * @return A LiveData object containing the latest game details.
     */
    public LiveData<String> getLatestGame() {
        return latestGame;
    }

    /**
     * Retrieves the lowest game details as LiveData.
     *
     * @return A LiveData object containing the lowest game details.
     */
    public LiveData<String> getLowestGame() {
        return lowestGame;
    }

    /**
     * Retrieves the highest game details as LiveData.
     *
     * @return A LiveData object containing the highest game details.
     */
    public LiveData<String> getHighestGame() {
        return highestGame;
    }

    /**
     * Retrieves all recorded scores as LiveData for populating the RecyclerView.
     *
     * @return A LiveData object containing a list of all recorded scores.
     */
    public LiveData<List<UserInfo>> getAllScores() {
        return allScores;
    }

    /**
     * Loads game statistics from the database asynchronously.
     * Fetches the latest, lowest, highest scores and all recorded scores and updates the LiveData objects.
     *
     * @param context The application context used to initialize the database.
     */
    public void loadGameStatistics(Context context) {
        // Initialize the database
        ScoreDatabase db = Room.databaseBuilder(
                context,
                ScoreDatabase.class,
                context.getString(R.string.databaseName)
        ).build();
        mDAO = db.scoreDAO();

        // Fetch data on a background thread
        new Thread(() -> {
            // Fetch latest game
            UserInfo latest = mDAO.getLatestUserInfo();
            latestGame.postValue(latest != null
                    ? context.getString(R.string.latest_game, latest.timestamp, latest.score)
                    : context.getString(R.string.no_data_available));

            // Fetch lowest game
            UserInfo lowest = mDAO.getLowestUserInfo();
            lowestGame.postValue(lowest != null
                    ? context.getString(R.string.lowest_game, lowest.timestamp, lowest.score)
                    : context.getString(R.string.no_data_available));

            // Fetch highest game
            UserInfo highest = mDAO.getHighestUserInfo();
            highestGame.postValue(highest != null
                    ? context.getString(R.string.highest_game, highest.timestamp, highest.score)
                    : context.getString(R.string.no_data_available));

            // Fetch all scores
            List<UserInfo> scores = mDAO.getAllUserInfo();
            allScores.postValue(scores); // Update LiveData with all scores
        }).start();
    }
}
