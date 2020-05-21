package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer {
//    which choice, use choice str instead of index to represent
    private String answerChoice;

    MultipleChoiceAnswer(String questionString, String answerChoice){
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
        this.answerChoice = answerChoice;
    }

    public String getAnswerChoice(){
        return this.answerChoice;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }
}
