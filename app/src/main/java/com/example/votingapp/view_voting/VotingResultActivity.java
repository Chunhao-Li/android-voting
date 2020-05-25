package com.example.votingapp.view_voting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.votingapp.R;
import com.example.votingapp.VotingAdapter;
import com.example.votingapp.data_type.question.QuestionType;
import com.example.votingapp.data_type.answer_stat.MultiChoiceStat;
import com.example.votingapp.data_type.answer_stat.QuestionStat;
import com.example.votingapp.data_type.answer_stat.TextQuestionStat;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.data_type.question.QuestionParcel;
import com.example.votingapp.data_type.question.TextQuestionParcel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VotingResultActivity extends AppCompatActivity {
    /**
     * This activity can show all the result of a created voting
     */
    private TextView votingIdText;
    private String title;
    private String votingId;

    private ArrayList<QuestionParcel> questionItems = new ArrayList<>();
    private ArrayList<QuestionStat> questionStatistics = new ArrayList<>();

    RecyclerView mRecyclerView;
    ResultAdapter mAdapter;

    private DatabaseReference mDatabaseAnswerRef;


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        /*
        This enables the users to copy the voting id.
         */
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

        // Initialize fields
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
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

        // Set voting title and voting id
        TextView votingTitle = findViewById(R.id.voting_result_title);
        votingIdText = findViewById(R.id.voting_result_id);
        registerForContextMenu(votingIdText);
        votingTitle.setText(title);
        votingIdText.setText(votingId);

        downloadAnswers();

    }


    private void downloadAnswers() {
        /*
        This method will download all the answers and save in the type belongs to the answer_stat
         */
        new ResultDownloadTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    class ResultDownloadTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onProgressUpdate(Void... values) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDatabaseAnswerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boolean updateQuestionItems = true; // Only update all the questions once
                    for (DataSnapshot votingAns : dataSnapshot.getChildren()) {
                        if (votingAns.child("Voting UID").getValue(String.class).equals(votingId)) {
                            DataSnapshot answersRef = votingAns.child("answers");
                            int questIndex = 0; // index of the question in the voting
                            for (DataSnapshot ansRef : answersRef.getChildren()) {
                                if (ansRef.child("question type").getValue(String.class)
                                        .equals(QuestionType.TEXT_QUESTION.name())) {
                                    // It is a text question
                                    String questionS = ansRef.child("question string").getValue(String.class);
                                    String answerText = ansRef.child("answer text").getValue(String.class);
                                    assert answerText != null;
                                    if (questionStatistics.size() <= questIndex) { // not yet collect all questions
                                        questionStatistics.add(new TextQuestionStat(questionS, answerText));
                                    } else {
                                        ((TextQuestionStat) questionStatistics.get(questIndex)).update(answerText);
                                    }
                                    if (updateQuestionItems) {
                                        questionItems.add(new TextQuestionParcel(questionS));
                                    }

                                } else {    // It is a multi choice question
                                    String questionS = ansRef.child("question string").getValue(String.class);
                                    if (questionStatistics.size() <= questIndex) {
                                        questionStatistics.add(new MultiChoiceStat(questionS));
                                    }
                                    MultiChoiceStat questStat = (MultiChoiceStat)
                                            questionStatistics.get(questIndex);
                                    ArrayList<String> choices = new ArrayList<>();  // collect all choices
                                    for (DataSnapshot choicesRef : ansRef.child("choices").getChildren()) {
                                        String choiceText = choicesRef.getKey();
                                        choices.add(choiceText);
                                        if (!questStat.existChoice(choiceText)) {
                                            questStat.addChoice(choiceText);
                                        }
                                        // update the stat when the choice is selected ("1")
                                        if (choicesRef.getValue(String.class).equals("1")) {
                                            questStat.update(choiceText);
                                        }
                                    }

                                    if (updateQuestionItems) {
                                        questionItems.add(new MultiChoiceParcel(questionS, choices));
                                    }
                                }
                                questIndex++;
                                publishProgress();
                            }
                            updateQuestionItems = false;    // questionItems have collected all the questions
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }
    }


}
