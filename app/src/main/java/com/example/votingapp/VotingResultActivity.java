package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.votingapp.data_type.QuestionType;
import com.example.votingapp.data_type.answer.Answer;
import com.example.votingapp.data_type.firebase_data.MultiChoiceQuestionStat;
import com.example.votingapp.data_type.firebase_data.QuestionStat;
import com.example.votingapp.data_type.firebase_data.TextQuestionStat;
import com.example.votingapp.voting_edit.MultiChoiceQuestionParcel;
import com.example.votingapp.voting_edit.QuestionParcel;
import com.example.votingapp.voting_edit.TextQuestionParcel;
import com.example.votingapp.voting_result.ResultAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VotingResultActivity extends AppCompatActivity {
    private TextView votingTitle;
    private TextView votingIdText;
    private String title;
    private String votingId;

    private ArrayList<ArrayList<Answer>> allAnswers = new ArrayList<>();
    private ArrayList<QuestionParcel> questionItems = new ArrayList<>();
    private ArrayList<QuestionStat> questionStatistics = new ArrayList<>();

    RecyclerView mRecyclerView;
    ResultAdapter mAdapter;

    // Firebase field
    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseAnswerRef;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, v.getId(), 0, "Copy");
        ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("votingId", votingIdText.getText());
        if (manager != null) {
            manager.setPrimaryClip(clipData);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_result);

        // Initialize firebase fields
        mDatabase = FirebaseDatabase.getInstance();
//        mDatabaseVotingRef = mDatabase.getReference("votings");
        mDatabaseAnswerRef = mDatabase.getReference("answers");

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            title = intent.getExtras().getString(VotingAdapter.RC_VOTING_TITLE);
            votingId = intent.getExtras().getString(VotingAdapter.RC_VOTING_ID);
        }

        // Initialize recycler view

        mRecyclerView = findViewById(R.id.voting_result_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ResultAdapter(this, questionItems, questionStatistics);
        mRecyclerView.setAdapter(mAdapter);

        // Initialize
        votingTitle = findViewById(R.id.voting_result_title);
        votingIdText = findViewById(R.id.voting_result_id);
        registerForContextMenu(votingIdText);
        votingTitle.setText(title);
        votingIdText.setText(votingId);

        downloadAnswers();

    }


    private void downloadAnswers() {
        new ResultDownloadTask().execute();
    }

    class ResultDownloadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onProgressUpdate(Void... values) {
            mAdapter.notifyDataSetChanged();
            Log.d("updateProgress", Integer.toString(questionItems.size()));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDatabaseAnswerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int answerIndex = -1;
                    boolean updateQuestionItems = true;
                    for (DataSnapshot votingAns : dataSnapshot.getChildren()) {
                        if (votingAns.child("Voting UID").getValue().toString().equals(votingId)) {
//                            allAnswers.add(new ArrayList<Answer>());

                            answerIndex++;
                            DataSnapshot answersRef = votingAns.child("answers");
                            int questIndex = 0;
                            for (DataSnapshot ansRef : answersRef.getChildren()) {
                                if (ansRef.child("question type").getValue().toString()
                                        .equals(QuestionType.TEXT_QUESTION.name())) {
                                    String questionS = ansRef.child("question string").getValue().toString();
                                    String answerText = ansRef.child("answer text").getValue().toString();
                                    if (questionStatistics.size() <= questIndex) {
                                        questionStatistics.add(new TextQuestionStat(questionS,  answerText));
                                    } else {
                                        ((TextQuestionStat) questionStatistics.get(questIndex)).update(answerText);
                                        Log.d("getanswer_text:", Integer.toString(((TextQuestionStat) questionStatistics.get(questIndex))
                                                .getAnswers().size()));
                                    }
                                    if (updateQuestionItems) {
                                        questionItems.add(new TextQuestionParcel(questionS));
                                    }
//                                    allAnswers.get(answerIndex).add(new TextAnswer(questionS, answerText));

                                } else {
//                                    // TODO MULTICHOICE answer
                                    String questionS = ansRef.child("question string").getValue().toString();
                                    if (questionStatistics.size() <= questIndex) {
                                        questionStatistics.add(new MultiChoiceQuestionStat(questionS));
                                    }
                                    MultiChoiceQuestionStat questStat = (MultiChoiceQuestionStat) questionStatistics.get(questIndex);
                                    ArrayList<String> choices = new ArrayList<>();

                                    for (DataSnapshot choicesRef : ansRef.child("choices").getChildren()) {
                                        String choiceText = choicesRef.getKey();
                                        choices.add(choiceText);
                                        if (!questStat.existChoice(choiceText)) {
                                            questStat.addChoice(choiceText);
                                        }
                                        if ( choicesRef.getValue().toString().equals("1")) {
                                            questStat.update(choiceText);
                                        }
                                        Log.d("VotingDownload_key", choicesRef.getKey());
                                        Log.d("VotingDownload_child", choicesRef.getValue().toString());
                                    }

                                    if (updateQuestionItems) {

                                        questionItems.add(new MultiChoiceQuestionParcel(questionS, choices));
                                    }
                                }
                                questIndex++;
                                publishProgress();
                            }
                            updateQuestionItems = false;
                        }
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
//
//                    Log.d("DoDataActivity", questionType.name());
//                    }
            });
            return null;
        }
    }


}
