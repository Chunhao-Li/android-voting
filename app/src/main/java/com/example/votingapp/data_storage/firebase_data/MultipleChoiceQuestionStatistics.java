package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;

import java.util.ArrayList;
import java.util.Arrays;

public class MultipleChoiceQuestionStatistics extends QuestionStatistics {
//    the name of each choice
    private ArrayList<String> choices;
//    # of voters vote for each choice, permute the same order as choices
    private ArrayList<Integer> choiceVoterCount;

//    inherit previous status
    public MultipleChoiceQuestionStatistics(String questionString, Integer totalNum, ArrayList<String> choices, ArrayList<Integer> choiceVoterCount) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
        super.totalVoterCount = totalNum;
        this.choices = choices;

        this.choiceVoterCount = choiceVoterCount;
    }
// if it's the first time to create this question, init statistics to default.
    public MultipleChoiceQuestionStatistics(String questionString, ArrayList<String> choices) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
        super.totalVoterCount = 0;
        this.choices = choices;
        int len = choices.size();
        Integer[] cvc = new Integer[len];
        Arrays.fill(cvc, 0);
        this.choiceVoterCount = new ArrayList<>(Arrays.asList(cvc));
    }



    //        int len = choices.size();
////        Integer[] cvc = new Integer[len];
////        Arrays.fill(cvc, 0);
//            this.choiceVoterCount = new Integer[choices.size()];

    @Override
    public Integer getTotalVoterCount(){
        return this.totalVoterCount;
    }

    @Override
    void update(Answer ans) {
        for(int i=0;i<this.choices.size();i++){
            if(this.choices.get(i).equals(ans.getAnswerString())){
                this.choiceVoterCount.set(choiceVoterCount.get(i)+1, i);

            }
        }
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

    public ArrayList<Integer> getChoiceVoterCount(){
        return this.choiceVoterCount;
    }

}
