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
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.Answer;
import com.example.votingapp.data_storage.firebase_data.MultipleChoiceAnswer;
import com.example.votingapp.data_storage.firebase_data.TextAnswer;
import com.example.votingapp.data_storage.firebase_data.UserAnswers;
import com.example.votingapp.voting_edit.EditMultiChoiceQuestion;
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
    private ArrayList<Answer> answers = new ArrayList<>();
    private static final String TAG = "DoVotingTAG";
    private int index = 0;
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mDatabaseVotingRef;
    private DatabaseReference mDatabaseAnswerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_voting);
        Log.d(TAG, Integer.toString(index)); // 0
        index += 1;
        // Initialize firebase fields
        mDatabase = FirebaseDatabase.getInstance();
        mDatabaseVotingRef = mDatabase.getReference("votings");
        mDatabaseAnswerRef = mDatabase.getReference("answers");


        mRecyclerView = findViewById(R.id.voting_do_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuestionAdapter(this, questionItems, answers);
        mRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        votingTitleTextView = findViewById(R.id.voting_do_title);
        if (intent.getExtras() != null) {
            votingId = intent.getExtras().getString(MainActivity.GET_VOTING_ID);
        }




        downloadQuestionItems();

        submitAnswers();
    }


    private void downloadQuestionItems() {
        DownloadQuestionTask downloadQuestionTask = new DownloadQuestionTask();
        downloadQuestionTask.execute();


    }

    class DownloadQuestionTask extends AsyncTask<Void, Void, Void> {


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
            mDatabaseVotingRef.child(votingId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("test_titleaaaa", dataSnapshot.child("votingTitle").getValue().toString());
                    votingTitle = dataSnapshot.child("votingTitle").getValue().toString();
                    votingTitleTextView.setText(votingTitle);
                    DataSnapshot questionRef = dataSnapshot.child("questions");

                    for (DataSnapshot questionChild : questionRef.getChildren()) {

                        QuestionType questionType = QuestionType.valueOf(questionChild.
                                child("questionType").getValue().toString());
                        String questionString = questionChild.child("question").getValue().toString();
                        Log.d("dovoting download", questionType.name());
//                            textAnswers.add("");

                        if (questionType == QuestionType.TEXT_QUESTION) {
                            EditTextQuestion editTextQuestion = new EditTextQuestion(questionString);

                            questionItems.add(new RecyclerViewQuestionItem(editTextQuestion,
                                    QuestionType.TEXT_QUESTION));
                            answers.add(new TextAnswer(questionString, ""));
                            publishProgress();
                        } else if (questionType == QuestionType.MULTI_CHOICE) {
                            ArrayList<String> choices = new ArrayList<>();
                            for (DataSnapshot choiceRef : questionChild.child("choices").getChildren()) {
                                choices.add(choiceRef.getValue().toString());
                                Log.d("dovoting choice", choiceRef.getValue().toString());
                            }
                            Log.d("choices+length", Integer.toString(choices.size()));
                            EditMultiChoiceQuestion editMultiChoiceQuestion = new EditMultiChoiceQuestion(
                                    questionString, choices);
                            questionItems.add(new RecyclerViewQuestionItem(editMultiChoiceQuestion,
                                    QuestionType.MULTI_CHOICE));
                            answers.add(new MultipleChoiceAnswer(questionString, ""));
                            publishProgress();
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

    //    extract answer from recycler view
    public ArrayList<Answer> extractAnswer() {
        ArrayList<Answer> answers = new ArrayList<>();
        int num = mRecyclerView.getAdapter().getItemCount();
        for (int i = 0; i < num; i++) {
            View currentAnswerView = mRecyclerView.findViewHolderForAdapterPosition(i).itemView;
            RecyclerViewQuestionItem currentQuestion = questionItems.get(i);
            String childViewName = currentAnswerView.getClass().getSimpleName();
            if (childViewName.equals("EditText")) {
//                currentAnswerView = (EditText) currentAnswerView;
                String currentAnswerText = ((EditText) currentAnswerView).getText().toString();
                TextAnswer curAns = new TextAnswer(currentQuestion.getData().questionString, currentAnswerText);
                answers.add(curAns);
            }
        }
        return answers;
    }

    //  if hit submit button
    public void submitAnswers() {

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("submitAnswers_test,", )

                Toast.makeText(getApplicationContext(), "Submit Button Triggered!", Toast.LENGTH_SHORT).show();
//                ArrayList<Answer> answers = extractAnswer();
                ArrayList<Answer> answers = DoVotingActivity.this.answers;
                for (Answer item : answers) {
                    if (item.getQuestionType().equals(QuestionType.TEXT_QUESTION)) {
                        String ansText = item.getAnswerString();
                        Log.d("answerText", item.getAnswerString());
                        TextAnswer tmp = new TextAnswer(item.getQuestionString(), ansText);
                        answers.add(tmp);
                    }
//                    else if(item.getQuestionType().equals(QuestionType.MULTI_CHOICE)){
//                        String ansChoice = item.getAnswerString();
//                        MultipleChoiceAnswer tmp = new MultipleChoiceAnswer(item.getQuestionString(),item.getAnswerString());
//                        answers.add(tmp);
//                    }
                }
                UserAnswers rtnAns = new UserAnswers(votingId, answers);
                Log.d("connecting to server...", "an");
                SaveAnswerOnCloud(rtnAns);
            }
        });
    }

    //    save user's answer to server
    public void SaveAnswerOnCloud(UserAnswers rtnAns) {
        DatabaseReference newVotingAnswerRef = mDatabaseAnswerRef.push();
        String votingAnswerKey = newVotingAnswerRef.getKey();
//        ready to connect
        newVotingAnswerRef.child("Voting UID").setValue(rtnAns.getVotingUid());
//        newVotingAnswerRef.child("Respondent UID").setValue(rtnAns.getRespondentUid());
        ArrayList<Answer> answers = rtnAns.getAnswers();
//        store answers in server
        for (int i = 0; i < answers.size(); i++) {
            Answer currentAns = answers.get(i);
            if (currentAns.getQuestionType().equals(QuestionType.TEXT_QUESTION)) {
                DatabaseReference curQuestionStatRef = newVotingAnswerRef.child("answers").child(Integer.toString(i));
                curQuestionStatRef.child("answer text").setValue(currentAns.getAnswerString());
                curQuestionStatRef.child("question string").setValue(currentAns.getQuestionString());
                curQuestionStatRef.child("question type").setValue(currentAns.getQuestionType());
            } else if (currentAns.getQuestionType().equals(QuestionType.MULTI_CHOICE)) {
                DatabaseReference curQuestionStatRef = newVotingAnswerRef.child("answers").child(Integer.toString(i));
                curQuestionStatRef.child("chosen choice").setValue(currentAns.getAnswerString());
                curQuestionStatRef.child("question string").setValue(currentAns.getQuestionString());
                curQuestionStatRef.child("question type").setValue(currentAns.getQuestionType());
            }
        }
        Toast.makeText(getApplicationContext(), "Successfully Submitted!" + answers.size(), Toast.LENGTH_SHORT).show();
        Intent intents = new Intent(this, MainActivity.class);
        startActivity(intents);
    }
}
