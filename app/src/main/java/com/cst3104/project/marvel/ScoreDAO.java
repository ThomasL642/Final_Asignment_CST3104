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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface ScoreDAO {

    @Insert
    void insertUserInfo(UserInfo userInfo);

    @Query("SELECT * FROM UserInfo")
    List<UserInfo> getAllUserInfo();

    @Delete
    void deleteUserInfo(UserInfo userInfo);

    @Query("DELETE FROM UserInfo")
    void deleteAllUserInfo();

}