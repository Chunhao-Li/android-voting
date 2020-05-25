package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MultiChoiceQuestStat extends QuestionStatistics {
//    the name of each choice
    private ArrayList<String> choices = new ArrayList<>();
//    # of voters vote for each choice, permute the same order as choices
    private ArrayList<Integer> choiceVoterCount = new ArrayList<>();
    private HashMap<String, Integer> choiceIndex = new HashMap<>();
    private int curIndex = -1;

//    inherit previous status
    public MultiChoiceQuestStat(String questionString) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;

//        super.totalVoterCount = totalNum;
    }
// if it's the first time to create this question, init statistics to default.
    public MultiChoiceQuestStat(String questionString, ArrayList<String> choices) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
        super.totalVoterCount = 0;
        this.choices = choices;
        int len = choices.size();
        Integer[] cvc = new Integer[len];
        Arrays.fill(cvc, 0);
        this.choiceVoterCount = new ArrayList<>(Arrays.asList(cvc));
    }


    public void addChoice(String choice) {
        choices.add(choice);
        curIndex++;
        choiceIndex.put(choice, curIndex);

        choiceVoterCount.add(0);
    }
    @Override
    public Integer getTotalVoterCount(){
        return this.totalVoterCount;
    }

    public void update(String choice) {
//        for(int i=0;i<this.choices.size();i++){
//            if(this.choices.get(i).equals(choice)) {
//                this.choiceVoterCount.set(i, choiceVoterCount.get(i)+1);
//            }
//        }
        if (choiceIndex.containsKey(choice)) {
            int index = choiceIndex.get( choice);
            choiceVoterCount.set(index, choiceVoterCount.get(index)+1);
        }
    }

    public boolean existChoice(String choice) {
        return choiceIndex.containsKey(choice);
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
