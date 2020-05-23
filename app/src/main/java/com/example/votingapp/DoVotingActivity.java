package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.voting_edit.EditTextQuestion;
import com.example.votingapp.voting_edit.QuestionAdapter;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DoVotingActivity extends AppCompatActivity {
    private TextView votingTitleTextView;
    private String votingTitle;
    private String votingId;
    private ArrayList<RecyclerViewQuestionItem> questionItems = new ArrayList<>();
    private static final String TAG = "DoVotingTAG";
    private int index = 0;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseVotingRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_voting);
        Log.d(TAG, Integer.toString(index)); // 0
        index += 1;
        // Initialize firebase fields
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseVotingRef = mDatabase.getReference("votings");

        Log.d(TAG, Integer.toString(index)); // 1
        index += 1;


        mRecyclerView = findViewById(R.id.voting_do_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuestionAdapter(this, questionItems);
        mRecyclerView.setAdapter(mAdapter);
        Log.d(TAG, Integer.toString(index)); // 2
        index += 1;

        Intent intent = getIntent();
        votingTitleTextView = findViewById(R.id.voting_do_title);
        if (intent.getExtras() != null) {
            votingId = intent.getExtras().getString(MainActivity.GET_VOTING_ID);
        }
        // Initialize

        Log.d("AMazingWhy", votingId);


        ;


        downloadQuestionItems();
//        mRecyclerView = findViewById(R.id.recyclerview_edit);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mAdapter = new QuestionAdapter(this, questionItems);
//        mRecyclerView.setAdapter(mAdapter);
    }

    private void downloadQuestionItems() {
        DownloadQuestionTask downloadQuestionTask = new DownloadQuestionTask(this);
        downloadQuestionTask.execute();


    }

    class DownloadQuestionTask extends AsyncTask<Void, Void, Void> {
        private Context mContext;

        DownloadQuestionTask(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("doInBackground,,", votingId);
            mDatabaseVotingRef.child(votingId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot votingChild : dataSnapshot.getChildren()) {
                        Log.d("test_titleaaaa", dataSnapshot.child("votingTitle").getValue().toString());
                        votingTitle = dataSnapshot.child("votingTitle").getValue().toString();

                        votingTitleTextView.setText(votingTitle);
                        DataSnapshot questionRef = dataSnapshot.child("questions");

                        for (DataSnapshot questionChild : questionRef.getChildren()) {

                            QuestionType questionType = QuestionType.valueOf(questionChild.
                                    child("questionType").getValue().toString());
                            String questionString = questionChild.child("question").getValue().toString();
                            if (questionType == QuestionType.TEXT_QUESTION) {
                                EditTextQuestion editTextQuestion = new EditTextQuestion(questionString);
                                Log.d("test_Do_activity", editTextQuestion.getQuestionString());
                                questionItems.add(new RecyclerViewQuestionItem(editTextQuestion,
                                        QuestionType.TEXT_QUESTION));
                                publishProgress();
                            } else {
                                // TODO MULTIPLE CHOICE
                            }
//                    Log.d("test_Do_activity", questionChild.child("questionType").getValue().toString());
                        }
//
//                    Log.d("DoDataActivity", questionType.name());
//                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }

    }
}
