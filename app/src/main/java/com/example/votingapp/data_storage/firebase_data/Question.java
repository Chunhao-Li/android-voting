package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

public abstract class Question {
    protected QuestionType questionType;
    //     question description
    public  String questionString;
    public abstract QuestionType getQuestionType();
    public abstract String getQuestionString();
}
