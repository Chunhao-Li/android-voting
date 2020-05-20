package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

public abstract class Question {
    protected QuestionType questionType;
    //     question description
    protected String questionString;
    protected abstract QuestionType getQuestionType();
    protected abstract String getQuestionString();
}
