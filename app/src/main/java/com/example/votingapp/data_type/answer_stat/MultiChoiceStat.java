package com.example.votingapp.data_type.answer_stat;

import com.example.votingapp.data_type.question.QuestionType;

import java.util.ArrayList;
import java.util.HashMap;

public class MultiChoiceStat extends QuestionStat {
    //    the name of each choice
    private ArrayList<String> choices = new ArrayList<>();
    //    # of voters vote for each choice, permute the same order as choices
    private ArrayList<Integer> choiceVoterCount = new ArrayList<>();
    private HashMap<String, Integer> choiceIndex = new HashMap<>();
    private int curIndex = -1;

    //    inherit previous status
    public MultiChoiceStat(String questionString) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
    }


    public void addChoice(String choice) {
        choices.add(choice);
        curIndex++;
        choiceIndex.put(choice, curIndex);
        choiceVoterCount.add(0);
    }

    public void update(String choice) {
        if (choiceIndex.containsKey(choice)) {
            int index;
            index = choiceIndex.get(choice);
            choiceVoterCount.set(index, choiceVoterCount.get(index) + 1);
        }
    }

    public boolean existChoice(String choice) {
        return choiceIndex.containsKey(choice);
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getQuestionTitle() {
        return this.questionString;
    }

    public ArrayList<String> getChoices() {
        return this.choices;
    }

    public ArrayList<Integer> getChoiceVoterCount() {
        return this.choiceVoterCount;
    }

}
