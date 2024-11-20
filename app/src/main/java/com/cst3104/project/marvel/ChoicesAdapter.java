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

    public class ChoicesAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<Marvel> avengers;

        public ChoicesAdapter(Context context, ArrayList<Marvel> avengers) {
            this.context = context;
            this.avengers = avengers;
        }

        @Override
        public int getCount() {
            return avengers.size();
        }

        @Override
        public Object getItem(int position) {
            return avengers.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.choices_layout, parent, false);
            }

            // Get the current Marvel object
            Marvel avenger = avengers.get(position);

            // Find the TextView
            TextView choiceView = convertView.findViewById(R.id.choicesView);

            // Set the name of the Avenger in the TextView
            //Log.d("AvengerName", avenger.getName());
            choiceView.setText(avenger.toString());
            choiceView.setVisibility(View.VISIBLE);

            return convertView;
        }
    }

