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

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {UserInfo.class}, version = 1)
public abstract class ScoreDatabase extends RoomDatabase {

    public abstract ScoreDAO scoreDAO();

}