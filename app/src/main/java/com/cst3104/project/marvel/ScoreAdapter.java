/**
 * Full Name: Hamza Habiballah
 *
 * Student ID: 041120464
 *
 * Course: CST3104
 *
 * Term: Fall 2024
 *
 * Assignment: Team Project
 *
 * Date: November 24, 2024
 */

package com.cst3104.project.marvel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst3104.project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter class for managing and displaying a list of user scores in a RecyclerView.
 * It binds the data to the views within each RecyclerView item.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    /**
     * List of user information objects to be displayed.
     */
    private final List<UserInfo> userInfoList;

    /**
     * Constructor for initializing the adapter with a list of user information.
     *
     * @param userInfoList The initial list of user scores to display.
     */
    public ScoreAdapter(List<UserInfo> userInfoList) {
        this.userInfoList = new ArrayList<>(userInfoList); // Ensure the list is mutable
    }

    /**
     * Called when a new ViewHolder is created to represent an item in the RecyclerView.
     *
     * @param parent   The parent view group.
     * @param viewType The view type of the new View.
     * @return A new instance of ScoreViewHolder.
     */
    @NonNull
    @Override
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_score, parent, false);
        return new ScoreViewHolder(view);
    }

    /**
     * Binds the data for a specific position in the RecyclerView to the ViewHolder.
     *
     * @param holder   The ViewHolder to bind data to.
     * @param position The position of the item in the RecyclerView.
     */
    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {
        UserInfo userInfo = userInfoList.get(position);
        holder.usernameTextView.setText(userInfo.userName);
        holder.scoreTextView.setText(String.valueOf(userInfo.score));
        holder.timestampTextView.setText(userInfo.timestamp);
    }

    /**
     * Returns the total number of items in the RecyclerView.
     *
     * @return The size of the userInfoList.
     */
    @Override
    public int getItemCount() {
        return userInfoList.size();
    }

    /**
     * Updates the list of user scores and notifies the RecyclerView to refresh.
     *
     * @param newScores The updated list of user scores.
     */
    public void setScores(List<UserInfo> newScores) {
        userInfoList.clear(); // Clear the existing list
        userInfoList.addAll(newScores); // Add all new scores
        notifyDataSetChanged(); // Notify the adapter to refresh the data
    }

    /**
     * ViewHolder class for managing the views of each item in the RecyclerView.
     */
    public static class ScoreViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView for displaying the username.
         */
        TextView usernameTextView;

        /**
         * TextView for displaying the score.
         */
        TextView scoreTextView;

        /**
         * TextView for displaying the timestamp.
         */
        TextView timestampTextView;

        /**
         * Constructor for initializing the ViewHolder with item views.
         *
         * @param itemView The view representing a single item in the RecyclerView.
         */
        public ScoreViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            scoreTextView = itemView.findViewById(R.id.textViewScore);
            timestampTextView = itemView.findViewById(R.id.textViewTimestamp);
        }
    }
}
