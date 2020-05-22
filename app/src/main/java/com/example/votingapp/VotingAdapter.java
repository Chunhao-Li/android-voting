package com.example.votingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.data_storage.firebase_data.VotingResult;

import java.util.ArrayList;
import java.util.HashMap;

public class VotingAdapter extends RecyclerView.Adapter<VotingAdapter.VotingHolder> {
    private Context mContext;
    // data
    private final ArrayList<String> votings;
    public static final String RC_VOTING_ID = "com.example.votingapp.VotingAdapterId";
    public static final String RC_VOTING_TITLE = "com.example.votingapp.VotingAdapterTitle";


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
        final String[] votingInfo = votings.get(position).split(",");
        String title = votingInfo[1];
        holder.votingTitle.setText(title);
        holder.viewVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VotingResultActivity.class);
                intent.putExtra(RC_VOTING_ID,votingInfo[0]);
                intent.putExtra(RC_VOTING_TITLE,votingInfo[1]);
                mContext.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return votings.size();
    }


    class VotingHolder extends RecyclerView.ViewHolder {
        TextView votingTitle;
        Button viewVoting;

        public VotingHolder(@NonNull View itemView) {
            super(itemView);
            votingTitle = itemView.findViewById(R.id.voting_title);
            viewVoting = itemView.findViewById(R.id.voting_view_button);


        }


    }
}
