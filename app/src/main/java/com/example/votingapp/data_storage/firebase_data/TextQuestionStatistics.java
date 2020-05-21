package com.example.votingapp.data_storage.firebase_data;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;

import java.util.HashMap;

public class TextQuestionStatistics extends QuestionStatistics {
//    a hash map that stores all answer strings with user UID as key
    private HashMap<String,String> answers;

    public TextQuestionStatistics(String question, Integer totalNum, HashMap<String,String> answers){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
        super.totalVoterCount = totalNum;
        this.answers = answers;
    }

    public HashMap<String,String> getAnwsers(){
        return this.answers;
    }

    @Override
    public Integer getTotalVoterCount() {
        return this.totalVoterCount;
    }

    @Override
    void update(Answer ans) {
        this.answers.put(ans.respondentUid,ans.getAnswerString());
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
