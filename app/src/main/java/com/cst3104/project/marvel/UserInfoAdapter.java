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
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cst3104.project.R;

import java.util.ArrayList;

public class UserInfoAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<UserInfo> UsersInfo;

    public UserInfoAdapter(Context context, ArrayList<UserInfo> usersInfo) {
        this.context = context;
        this.UsersInfo = usersInfo;
    }

    @Override
    public int getCount() {
        return UsersInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return UsersInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.scorebroad_layout, parent, false);
        }

        // Get the current Marvel object
        UserInfo userInfo = UsersInfo.get(position);

        // Find the TextView
        TextView scorebroadView = convertView.findViewById(R.id.scorebroadView);

        String formatted_string = format_score_string(userInfo.userName, userInfo.score);

        // Set the name of the Player in the TextView
        scorebroadView.setText(formatted_string);
        scorebroadView.setVisibility(View.VISIBLE);
        // Set the score of the Player in the TextView



        return convertView;
    }

    private String format_score_string(String userName, int score) {
        int totalLength = 33;
        int spaces = totalLength - (userName.length() + String.valueOf(score).length());
        String spacesString = new String(new char[spaces]).replace("\0", " "); // Generate spaces
        return userName + spacesString + score;
    }
}

