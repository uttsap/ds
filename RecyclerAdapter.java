package com.afeastoffriends.doctorsaathi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rajan on 12/18/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> { //recyclerview holder describes an item view about its place in recycler view
    ArrayList<String> diseases_name = new ArrayList<>();
    private Context context;
    public RecyclerAdapter(ArrayList<String>diseases_name,Context context) //constructor
    {
        this.diseases_name=diseases_name;
        this.context=context;

    }

    public RecyclerAdapter() {

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //create new views
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout,parent,false); //set the view sizes, parameters, paddings.
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) { //replace the contents of the view
        // // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Activity activity = (Activity) context;
        final Intent intent = new Intent(context,Details.class);
        holder.Tx_diseases.setText(diseases_name.get(position));
        holder.Tx_diseases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("position",position+1);
                activity.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return diseases_name.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder
    {
        TextView Tx_diseases;
        public RecyclerViewHolder(View view) {


            super(view);
            Tx_diseases = (TextView) view.findViewById(R.id.text);
            Tx_diseases.setTextSize(15);

        }
    }
}