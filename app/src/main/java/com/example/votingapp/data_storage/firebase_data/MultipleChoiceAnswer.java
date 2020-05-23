package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

import java.util.ArrayList;

public class MultipleChoiceAnswer extends Answer {
//    which choice, use choice str instead of index to represent
    private String answerChoice;

    public MultipleChoiceAnswer(String questionString, String answerChoice){
        super.questionString = questionString;
        super.questionType = QuestionType.MULTI_CHOICE;
//        super.respondentUid = respondentUid;
        this.answerChoice = answerChoice;
    }

//    @Override
//    public String getRespondentUid() {
//        return this.respondentUid;
//    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getAnswerString() {
        return this.answerChoice;
    }
}
