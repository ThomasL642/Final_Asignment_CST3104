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
