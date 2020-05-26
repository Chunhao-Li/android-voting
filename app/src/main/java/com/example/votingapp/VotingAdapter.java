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


import com.example.votingapp.view_voting.VotingResultActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This adapter helps show all the voting created by the current user
 */
public class VotingAdapter extends RecyclerView.Adapter<VotingAdapter.VotingHolder> {

    private Context mContext;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    // data
    private final ArrayList<ArrayList<String>> votings;
    public static final String RC_VOTING_ID = "com.example.votingapp.VotingAdapterId";
    public static final String RC_VOTING_TITLE = "com.example.votingapp.VotingAdapterTitle";


    VotingAdapter(Context mContext, ArrayList<ArrayList<String>> votings) {
        this.mContext = mContext;
        this.votings = votings;
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

    }

    @NonNull
    @Override
    public VotingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.main_voting_item, parent, false);
        return new VotingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VotingHolder holder, final int position) {
        // set voting title and initialize the buttons
        final ArrayList<String> curVotingInfo = votings.get(position);
        String title = curVotingInfo.get(1);
        holder.votingTitle.setText(title);
        holder.viewVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, VotingResultActivity.class);
                intent.putExtra(RC_VOTING_ID, curVotingInfo.get(0));
                intent.putExtra(RC_VOTING_TITLE, curVotingInfo.get(1));
                mContext.startActivity(intent);
            }
        });
        holder.deleteVoting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteVoting(position, curVotingInfo.get(0));

            }
        });
    }

    private void deleteVoting(int position, final String votingId) {
        /*
        Delete the voting when pressed the button
         */
        votings.remove(position);

        final DatabaseReference votingsRef = mDatabase.getReference("votings");
        DatabaseReference userRef = mDatabase.getReference("users");
        votingsRef.child(votingId).setValue(null);
        if (mAuth.getCurrentUser() != null) {
            String userId = Integer.toString(mAuth.getCurrentUser().getEmail().hashCode());

            userRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("votings")) {
                        DataSnapshot votingChild = dataSnapshot.child("votings");
                        for (DataSnapshot curVoting : votingChild.getChildren()) {
                            String curVotingId = curVoting.getValue(String.class);
                            if (curVotingId.equals(votingId)) {
                                curVoting.getRef().removeValue();
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        this.notifyItemRemoved(position);
    }


    @Override
    public int getItemCount() {
        return votings.size();
    }


    static class VotingHolder extends RecyclerView.ViewHolder {
        TextView votingTitle;
        Button viewVoting;
        Button deleteVoting;

        VotingHolder(@NonNull View itemView) {
            super(itemView);
            votingTitle = itemView.findViewById(R.id.voting_title);
            viewVoting = itemView.findViewById(R.id.button_view_voting);
            deleteVoting = itemView.findViewById(R.id.button_delete_voting);
        }
    }

}
