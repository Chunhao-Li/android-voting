package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;

import java.util.ArrayList;

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

}
