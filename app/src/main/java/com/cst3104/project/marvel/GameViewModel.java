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

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

/**
 * ViewModel for managing the game state and logic in the Avengers game.
 * It maintains the current game data, such as the list of Avengers,
 * the winning Avenger, and the player's progress.
 */
public class GameViewModel extends ViewModel {

    /**
     * Counter for tracking the number of attempts.
     */
    public int counter = 0;

    /**
     * Flag to indicate if the correct answer has been chosen.
     */
    public boolean rightAnswerChosen = false;

    /**
     * The current winning Avenger for the game.
     */
    public Marvel WinningAvenger;

    /**
     * List of current Avengers displayed as choices in the game.
     */
    public ArrayList<Marvel> CurrentAvengers = new ArrayList<>();

    /**
     * Resets the game state, including the counter, the list of Avengers, and the winning Avenger.
     *
     * @param avengers        List of all available Avengers.
     * @param numberOfChoices The number of choices to display during the game.
     */
    public void resetGame(ArrayList<Marvel> avengers, int numberOfChoices) {
        // Reset the counter and correct answer flag
        counter = 0;
        rightAnswerChosen = false;

        // Select a new winning Avenger
        WinningAvenger = getRandomMarvel(avengers);

        // Update the list of choices
        updateAvengerChoices(avengers, numberOfChoices);
    }

    /**
     * Selects a random Marvel character from the provided list.
     *
     * @param avengers List of available Avengers.
     * @return A randomly selected Marvel object.
     */
    public Marvel getRandomMarvel(ArrayList<Marvel> avengers) {
        return avengers.get((int) (Math.random() * avengers.size()));
    }

    /**
     * Updates the list of current Avengers displayed as choices.
     * Ensures that the list does not contain duplicates and includes the winning Avenger
     * at a random position in the list.
     *
     * @param avengers        List of all available Avengers.
     * @param numberOfChoices The number of choices to display during the game.
     */
    public void updateAvengerChoices(ArrayList<Marvel> avengers, int numberOfChoices) {
        // Clear the existing list of choices
        CurrentAvengers.clear();

        // Add random Avengers to the list until the desired number is reached
        while (CurrentAvengers.size() < numberOfChoices - 1) {
            Marvel randomAvenger = getRandomMarvel(avengers);

            // Avoid duplicates in the list
            if (!CurrentAvengers.contains(randomAvenger) && !CurrentAvengers.contains(WinningAvenger)) {
                CurrentAvengers.add(randomAvenger);
            }
        }

        // Randomly insert the winning Avenger into the list
        int randomIndex = (int) (Math.random() * numberOfChoices);
        CurrentAvengers.add(randomIndex, WinningAvenger);
    }
}
