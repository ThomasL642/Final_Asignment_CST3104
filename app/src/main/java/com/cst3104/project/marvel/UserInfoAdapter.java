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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cst3104.project.R;

import java.util.ArrayList;

/**
 * Custom adapter for displaying user information in a ListView.
 * This adapter is used to bind data from the `UserInfo` objects to the
 * `scorebroad_layout` for the leaderboard display.
 */
public class UserInfoAdapter extends BaseAdapter {

    /**
     * The context of the activity or fragment.
     */
    private Context context;

    /**
     * List of `UserInfo` objects representing the user scores.
     */
    private ArrayList<UserInfo> UsersInfo;

    /**
     * Constructs a new `UserInfoAdapter` with the given context and user information list.
     *
     * @param context   The context of the activity or fragment.
     * @param usersInfo List of `UserInfo` objects to be displayed.
     */
    public UserInfoAdapter(Context context, ArrayList<UserInfo> usersInfo) {
        this.context = context;
        this.UsersInfo = usersInfo;
    }

    /**
     * Gets the total number of items in the adapter.
     *
     * @return The size of the `UsersInfo` list.
     */
    @Override
    public int getCount() {
        return UsersInfo.size();
    }

    /**
     * Gets the item at the specified position in the list.
     *
     * @param position The position of the item.
     * @return The `UserInfo` object at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return UsersInfo.get(position);
    }

    /**
     * Gets the ID of the item at the specified position.
     *
     * @param position The position of the item.
     * @return The position of the item as its ID.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Generates the view for each item in the ListView.
     *
     * @param position    The position of the item in the list.
     * @param convertView The old view to reuse, if possible.
     * @param parent      The parent view that this view will be attached to.
     * @return The updated view for the current item.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            // Inflate the custom layout for each item
            convertView = LayoutInflater.from(context).inflate(R.layout.scorebroad_layout, parent, false);
        }

        // Get the current `UserInfo` object
        UserInfo userInfo = UsersInfo.get(position);

        // Find the TextView for displaying the formatted score
        TextView scorebroadView = convertView.findViewById(R.id.scorebroadView);

        // Format the score string and set it in the TextView
        String formattedString = formatScoreString(userInfo.userName, userInfo.score);
        scorebroadView.setText(formattedString);
        scorebroadView.setVisibility(View.VISIBLE);

        return convertView;
    }

    /**
     * Formats a string for the scoreboard by aligning the username and score with spacing.
     *
     * @param userName The name of the user.
     * @param score    The score of the user.
     * @return A formatted string with the username and score aligned.
     */
    private String formatScoreString(String userName, int score) {
        int totalLength = 33; // Total length for alignment
        int spaces = totalLength - (userName.length() + String.valueOf(score).length());
        String spacesString = new String(new char[spaces]).replace("\0", " "); // Generate spaces
        return userName + spacesString + score;
    }
}
