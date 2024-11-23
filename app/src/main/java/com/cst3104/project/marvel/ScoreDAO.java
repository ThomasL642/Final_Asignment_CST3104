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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for performing database operations on the UserInfo table.
 * This interface defines methods for inserting, retrieving, and deleting user information.
 */
@Dao
public interface ScoreDAO {

    /**
     * Retrieves the most recent UserInfo entry based on the timestamp.
     *
     * @return The latest UserInfo entry, ordered by timestamp in descending order.
     */
    @Query("SELECT * FROM UserInfo ORDER BY timestamp DESC LIMIT 1")
    UserInfo getLatestUserInfo();

    /**
     * Retrieves the UserInfo entry with the lowest score.
     *
     * @return The UserInfo entry with the minimum score, ordered by score in ascending order.
     */
    @Query("SELECT * FROM UserInfo ORDER BY score ASC LIMIT 1")
    UserInfo getLowestUserInfo();

    /**
     * Retrieves the UserInfo entry with the highest score.
     *
     * @return The UserInfo entry with the maximum score, ordered by score in descending order.
     */
    @Query("SELECT * FROM UserInfo ORDER BY score DESC LIMIT 1")
    UserInfo getHighestUserInfo();


    /**
     * Inserts a new user record into the UserInfo table.
     *
     * @param userInfo The UserInfo object to insert into the database.
     */
    @Insert
    void insertUserInfo(UserInfo userInfo);

    /**
     * Retrieves all user records from the UserInfo table.
     *
     * @return A list of all UserInfo objects stored in the database.
     */
    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAllUserInfo();

    /**
     * Deletes a specific user record from the UserInfo table.
     *
     * @param userInfo The UserInfo object to delete from the database.
     */
    @Delete
    void deleteUserInfo(UserInfo userInfo);

    /**
     * Deletes all user records from the UserInfo table.
     */
    @Query("DELETE FROM UserInfo")
    void deleteAllUserInfo();
}
