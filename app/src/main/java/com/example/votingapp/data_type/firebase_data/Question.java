package com.example.votingapp.data_type.firebase_data;

import com.example.votingapp.data_type.QuestionType;

public abstract class Question {
    protected QuestionType questionType;
    //     question description
    protected   String questionString;
    public abstract QuestionType getQuestionType();
    public abstract String getQuestionString();
}
