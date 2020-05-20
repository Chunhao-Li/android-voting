package com.example.votingapp.data_storage;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.example.votingapp.data_storage.QuestionStatistics;
import com.example.votingapp.data_storage.QuestionType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

@SuppressLint("ParcelCreator")
public class MultipleChoiceQuestionStatistics extends QuestionStatistics {
//    the name of each choice
    private ArrayList<String> choices;
//    # of voters vote for each choice, permute the same order as choices
    private Integer[] choiceVoterCount;


    MultipleChoiceQuestionStatistics(String questionString, Integer totalNum, ArrayList<String> choices, Integer[] choiceVoterCount){
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
        super.totalVoterCount = totalNum;
        this.choices = choices;
        this.choiceVoterCount = choiceVoterCount;
    }
//            this.choiceVoterCount = new Integer[choices.size()];

    @Override
    public Integer getTotalVoterCount(){
        return this.totalVoterCount;
    }

    @Override
    public QuestionType getQuestionType(){
        return this.questionType;
    }

    @Override
    public String getQuestionString(){
        return this.questionString;
    }


    public ArrayList<String> getChoices(){
        return this.choices;
    }

    public Integer[] getChoiceVoterCount(){
        return this.choiceVoterCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
