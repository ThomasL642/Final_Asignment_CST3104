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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst3104.project.R;

import java.util.ArrayList;

/**
 * Adapter class for displaying a list of Marvel objects in a RecyclerView.
 */
public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoicesViewHolder> {

    /**
     * Context of the application or activity.
     */
    private Context context;

    /**
     * List of Marvel objects representing Avengers.
     */
    private ArrayList<Marvel> avengers;

    /**
     * Listener for handling item click events.
     */
    private OnItemClickListener listener;

    /**
     * Interface for defining item click behavior.
     */
    public interface OnItemClickListener {
        /**
         * Triggered when an item is clicked.
         *
         * @param position Position of the clicked item in the list.
         */
        void onItemClick(int position);
    }

    /**
     * Constructor to initialize the adapter with required data.
     *
     * @param context  The context of the activity or fragment.
     * @param avengers List of Marvel objects to display.
     * @param listener Listener for handling item click events.
     */
    public ChoicesAdapter(Context context, ArrayList<Marvel> avengers, OnItemClickListener listener) {
        this.context = context;
        this.avengers = avengers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChoicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        View view = LayoutInflater.from(context).inflate(R.layout.choices_layout, parent, false);
        return new ChoicesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChoicesViewHolder holder, int position) {
        // Get the current Marvel object
        Marvel avenger = avengers.get(position);

        // Set the name of the Avenger in the TextView
        holder.choiceView.setText(avenger.toString());
        holder.choiceView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        // Return the total number of Avengers in the list
        return avengers.size();
    }

    /**
     * ViewHolder class for managing individual list items.
     */
    class ChoicesViewHolder extends RecyclerView.ViewHolder {

        /**
         * TextView to display the name of an Avenger.
         */
        TextView choiceView;

        /**
         * Constructor for initializing the ViewHolder.
         *
         * @param itemView The view of the individual RecyclerView item.
         */
        public ChoicesViewHolder(@NonNull View itemView) {
            super(itemView);

            // Initialize the TextView for the Avenger's name
            choiceView = itemView.findViewById(R.id.choicesView);

            // Set an OnClickListener for the entire item view
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    // Ensure the position is valid before invoking the click listener
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
