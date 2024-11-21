package com.cst3104.project.marvel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Score {

    @ColumnInfo(name="Score")
    protected String message;

    @ColumnInfo(name="TimeStamp")
    protected String timestamp;

    @ColumnInfo(name="SendOrReceive")
    protected String sendOrReceive;

    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;

    public ChatMessage(String message, String sendOrReceive) {
        this.message = message;
        this.sendOrReceive = sendOrReceive;
        this.timeSent = getCurrentTime();  // Automatically set the current time
    }

    private String getCurrentTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }

    public int getId() {
        return id;
    }
}
