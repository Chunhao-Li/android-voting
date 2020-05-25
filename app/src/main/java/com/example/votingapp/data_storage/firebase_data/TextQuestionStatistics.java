package com.example.votingapp.data_storage.firebase_data;

import android.annotation.SuppressLint;
import android.os.Parcel;

import androidx.annotation.Nullable;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;

import java.util.ArrayList;
import java.util.HashMap;

public class TextQuestionStatistics extends QuestionStatistics {
//    a hash map that stores all answer strings with user UID as key
    private ArrayList<String> answers = new ArrayList<>();

//    if it's the first time to create this question, init statistics to default.
    public TextQuestionStatistics(String question){
    super.questionString = question;
    super.questionType = QuestionType.TEXT_QUESTION;
    super.totalVoterCount = 0;
    this.answers = new ArrayList<>();
    }

//    inherit previous status
    public TextQuestionStatistics(String question, Integer totalNum, ArrayList<String> answers){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
        super.totalVoterCount = totalNum;
        this.answers = answers;
    }

    public ArrayList<String> getAnswers(){
        return this.answers;
    }

    @Override
    public Integer getTotalVoterCount() {
        return this.totalVoterCount;
    }

    void update(String  ans) {
        this.answers.add(ans);
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }


}
