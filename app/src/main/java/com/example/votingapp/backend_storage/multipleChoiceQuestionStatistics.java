package com.example.votingapp.backend_storage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class multipleChoiceQuestionStatistics extends QuestionStatistics{
//    the name of each choice
    private ArrayList<String> choices;
//    # of voters vote for each choice, permute the same order as choices
    private Integer[] choiceVoterCount;


    multipleChoiceQuestionStatistics(String questionString, Integer totalNum, ArrayList<String> choices, Integer[] choiceVoterCount){
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
