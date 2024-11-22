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

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class UserInfo {

    @ColumnInfo(name="Score")
    protected int score;

    @ColumnInfo(name="TimeStamp")
    protected String timestamp;

    @ColumnInfo(name="UserName")
    protected String userName;

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public UserInfo(int score, String userName) {
        this.score = score;
        this.userName = userName;
        this.timestamp = getCurrentTime();  // Automatically set the current time
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public int getId() {
        return id;
    }
}
