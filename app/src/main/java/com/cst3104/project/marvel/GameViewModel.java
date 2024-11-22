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

import androidx.lifecycle.ViewModel;
import java.util.ArrayList;

public class GameViewModel extends ViewModel {
    public int counter = 0;
    public boolean rightAnswerChosen = false;
    public Marvel WinningAvenger;
    public ArrayList<Marvel> CurrentAvengers = new ArrayList<>();

    public void resetGame(ArrayList<Marvel> avengers, int numberOfChoices) {
        counter = 0;
        rightAnswerChosen = false;
        WinningAvenger = getRandomMarvel(avengers);
        updateAvengerChoices(avengers, numberOfChoices);
    }

    public Marvel getRandomMarvel(ArrayList<Marvel> avengers) {
        return avengers.get((int) (Math.random() * avengers.size()));
    }

    public void updateAvengerChoices(ArrayList<Marvel> avengers, int numberOfChoices) {
        CurrentAvengers.clear();
        while (CurrentAvengers.size() < numberOfChoices - 1) {
            Marvel randomAvenger = getRandomMarvel(avengers);
            if (!CurrentAvengers.contains(randomAvenger)) {
                CurrentAvengers.add(randomAvenger);
            }
        }
        int randomIndex = (int) (Math.random() * numberOfChoices);
        CurrentAvengers.add(randomIndex, WinningAvenger);
    }


}
