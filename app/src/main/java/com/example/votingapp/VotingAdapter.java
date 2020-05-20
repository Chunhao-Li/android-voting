package com.example.votingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VotingAdapter extends RecyclerView.Adapter<VotingAdapter.VotingHolder> {
    private Context mContext;
    // data
    private final ArrayList<String> votings;


    VotingAdapter(Context mContext, ArrayList<String> votings) {
        this.mContext = mContext;
        this.votings = votings;

    }
    @NonNull
    @Override
    public VotingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_voting_item, parent, false);
        return new VotingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VotingHolder holder, int position) {
        String title = votings.get(position);
        holder.votingTitle.setText(title);
    }


    @Override
    public int getItemCount() {
        return votings.size();
    }


    class VotingHolder extends RecyclerView.ViewHolder {
        TextView votingTitle;

        public VotingHolder(@NonNull View itemView) {
            super(itemView);
            votingTitle = itemView.findViewById(R.id.voting_title);
        }
    }
}
