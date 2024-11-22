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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cst3104.project.R;

import java.util.ArrayList;

public class ChoicesAdapter extends RecyclerView.Adapter<ChoicesAdapter.ChoicesViewHolder> {
    private Context context;
    private ArrayList<Marvel> avengers;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ChoicesAdapter(Context context, ArrayList<Marvel> avengers, OnItemClickListener listener) {
        this.context = context;
        this.avengers = avengers;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChoicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
        return avengers.size();
    }

    class ChoicesViewHolder extends RecyclerView.ViewHolder {
        TextView choiceView;

        public ChoicesViewHolder(@NonNull View itemView) {
            super(itemView);
            choiceView = itemView.findViewById(R.id.choicesView);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
