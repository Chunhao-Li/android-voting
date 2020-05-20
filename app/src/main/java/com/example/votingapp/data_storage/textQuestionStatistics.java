package com.example.votingapp.data_storage;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.votingapp.data_storage.QuestionStatistics;
import com.example.votingapp.data_storage.QuestionType;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("ParcelCreator")
public class textQuestionStatistics extends QuestionStatistics {
//    a hash map that stores all answer strings with user UID as key
    private HashMap<String,String> answers;

    public textQuestionStatistics(String question, Integer totalNum, HashMap<String,String> answers){
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
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
