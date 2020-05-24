package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.Answer;
import com.example.votingapp.data_storage.firebase_data.MultipleChoiceAnswer;
import com.example.votingapp.data_storage.firebase_data.MultipleChoiceQuestion;
import com.example.votingapp.data_storage.firebase_data.TextAnswer;
import com.example.votingapp.voting_edit.EditMultiChoiceQuestion;
import com.example.votingapp.voting_edit.EditTextQuestion;
import com.example.votingapp.voting_edit.QuestionAdapter;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;
import com.example.votingapp.voting_result.ResultAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class VotingResultActivity extends AppCompatActivity {
    private TextView votingTitle;
    private TextView votingIdText;
    private String title;
    private String votingId;

    private ArrayList<ArrayList<Answer>> allAnswers = new ArrayList<>();
    private ArrayList<RecyclerViewQuestionItem> questionItems = new ArrayList<>();

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
            questionItems = intent.getParcelableArrayListExtra(VotingAdapter.RC_QUESTION_ITEMS);
        }

        // Initialize recycler view

        mRecyclerView = findViewById(R.id.voting_result_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ResultAdapter(this,questionItems, allAnswers);
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
                            allAnswers.add(new ArrayList<Answer>());
                            answerIndex++;
                            DataSnapshot answersRef = votingAns.child("answers");
                            for (DataSnapshot ansRef : answersRef.getChildren()) {
                                if (ansRef.child("question type").getValue().toString()
                                        .equals(QuestionType.TEXT_QUESTION.name())) {
                                    String questionS = ansRef.child("question string").getValue().toString();
                                    String answerText = ansRef.child("answer text").getValue().toString();
                                    if (updateQuestionItems) {
                                        EditTextQuestion textQuestion = new EditTextQuestion(questionS);
                                        questionItems.add(new RecyclerViewQuestionItem(textQuestion,
                                                QuestionType.TEXT_QUESTION));
                                    }
                                    allAnswers.get(answerIndex).add(new TextAnswer(questionS, answerText));
                                    Log.d("getanswer_text:", answerText);
                                } else {
//                                    // TODO MULTICHOICE answer
                                    String questionS = ansRef.child("question string").getValue().toString();
                                    if (updateQuestionItems) {
                                        ArrayList<String> choices = new ArrayList<>();
                                        choices.add("test_choice1");
                                        choices.add("test_choice2");
                                        choices.add("test_choice3");
                                        choices.add("test_choice4");
                                        EditMultiChoiceQuestion multiQ = new EditMultiChoiceQuestion(questionS
                                                        , choices);
                                        questionItems.add(new RecyclerViewQuestionItem(
                                                multiQ, QuestionType.MULTI_CHOICE));
                                    }
                                }
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
