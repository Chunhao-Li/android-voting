package com.example.votingapp.data_type.answer;

import com.example.votingapp.data_type.question.QuestionType;

import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer {
    //    which choice, use choice str instead of index to represent
    private ArrayList<ArrayList<String>> answerChoice;

    public MultipleChoiceAnswer(String questionString, ArrayList<ArrayList<String>> answerChoice) {
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
//        super.respondentUid = respondentUid;
        this.answerChoice = answerChoice;
    }


    @Override
    public String getQuestionString() {
        return this.questionString;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public ArrayList<ArrayList<String>> getAnswerChoice() {
        return this.answerChoice;
    }
}