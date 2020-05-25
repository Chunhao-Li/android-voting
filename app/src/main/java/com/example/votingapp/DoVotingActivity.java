package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.votingapp.data_type.question.QuestionType;
import com.example.votingapp.data_type.answer.Answer;
import com.example.votingapp.data_type.answer.MultipleChoiceAnswer;
import com.example.votingapp.data_type.answer.TextAnswer;
import com.example.votingapp.data_type.answer.UserAnswers;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.data_type.question.QuestionParcel;
import com.example.votingapp.data_type.question.TextQuestionParcel;
import com.example.votingapp.edit_voting.QuestionAdapter;
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
    private ArrayList<QuestionParcel> questionItems = new ArrayList<>();
    private ArrayList<Answer> answers = new ArrayList<>();
    private static final String TAG = "DoVotingTAG";
    private int index = 0;
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


        RecyclerView mRecyclerView = findViewById(R.id.voting_do_recyclerview);
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

                            questionItems.add(new TextQuestionParcel(questionString));
                            answers.add(new TextAnswer(questionString, ""));
                            publishProgress();
                        } else if (questionType == QuestionType.MULTI_CHOICE) {
                            ArrayList<String> choices = new ArrayList<>();
                            for (DataSnapshot choiceRef : questionChild.child("choices").getChildren()) {
                                choices.add(choiceRef.getValue().toString());
                                Log.d("dovoting choice", choiceRef.getValue().toString());
                            }
                            Log.d("choices+length", Integer.toString(choices.size()));
                            questionItems.add(new MultiChoiceParcel(
                                    questionString, choices));
                            // TODO save multi choice answer
                            ArrayList<ArrayList<String>> answerChoices = new ArrayList<>();
                            for (int i = 0; i < choices.size(); i++) {
                                answerChoices.add(new ArrayList<String>());
                                answerChoices.get(i).add(choices.get(i));
                                answerChoices.get(i).add("0");  // default: all unchecked
                            }
                            answers.add(new MultipleChoiceAnswer(questionString, answerChoices));
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

//    //    extract answer from recycler view
//    public ArrayList<Answer> extractAnswer() {
//        ArrayList<Answer> answers = new ArrayList<>();
//
//        int num = mRecyclerView.getChildCount();
//        Toast.makeText(getApplicationContext(), num, Toast.LENGTH_SHORT).show();
//        for(int i=0;i<num;i++){
//            View currentAnswerView = mRecyclerView.findViewHolderForAdapterPosition(i).itemView;
//            RecyclerViewQuestionItem currentQuestion = questionItems.get(i);
//            String childViewName = currentAnswerView.getClass().getSimpleName();
//            if (childViewName.equals("EditText")) {
////                currentAnswerView = (EditText) currentAnswerView;
//                String currentAnswerText = ((EditText) currentAnswerView).getText().toString();
//                TextAnswer curAns = new TextAnswer(currentQuestion.getData().questionString, currentAnswerText);
//                answers.add(curAns);
//            }
//        }
//        return answers;
//    }

    //  if hit submit button
    public void submitAnswers() {
//        for (Answer answer: answers) {
//            answer.getQuestionString();
//        }

        Button buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("submitAnswers_test,", )

                Toast.makeText(getApplicationContext(), "Submit Button Triggered!", Toast.LENGTH_SHORT).show();

                ArrayList<Answer> answers = DoVotingActivity.this.answers;
                UserAnswers rtnAns = new UserAnswers(votingId,answers);
                Log.d("connecting to server...","...");
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
                curQuestionStatRef.child("answer text").setValue(((TextAnswer)currentAns).getAnswerString());
                curQuestionStatRef.child("question string").setValue(currentAns.getQuestionString());
                curQuestionStatRef.child("question type").setValue(currentAns.getQuestionType());
            } else if (currentAns.getQuestionType().equals(QuestionType.MULTI_CHOICE)) {
                ArrayList<ArrayList<String>> answerChoices = ((MultipleChoiceAnswer)currentAns).getAnswerChoice();
                Log.d("dovoting_multi", answerChoices.get(0).get(1));
                DatabaseReference curQuestionStatRef = newVotingAnswerRef.child("answers").child(Integer.toString(i));
                curQuestionStatRef.child("question string").setValue(currentAns.getQuestionString());
                curQuestionStatRef.child("question type").setValue(currentAns.getQuestionType());
                ArrayList<ArrayList<String>> choices = ((MultipleChoiceAnswer)currentAns).getAnswerChoice();
                for(int j = 0;j<choices.size();j++){
                    curQuestionStatRef.child("choices").child(choices.get(j).get(0)).setValue(choices.get(j).get(1));
                }
            }
        }
        Toast.makeText(getApplicationContext(), "Successfully Submitted! ", Toast.LENGTH_SHORT).show();
        Intent intents = new Intent(this, MainActivity.class);
        startActivity(intents);
    }
}
