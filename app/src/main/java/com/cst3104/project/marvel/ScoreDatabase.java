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

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Room database class for managing the application's local database.
 * This database stores user information in the `UserInfo` table.
 */
@Database(entities = {UserInfo.class}, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {

    /**
     * Provides access to the DAO for performing database operations.
     *
     * @return An instance of the ScoreDAO interface for accessing the UserInfo table.
     */
    public abstract ScoreDAO scoreDAO();
}
