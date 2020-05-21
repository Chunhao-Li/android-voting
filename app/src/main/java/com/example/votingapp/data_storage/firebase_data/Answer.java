package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

abstract class Answer {
    protected  String questionString;
    protected QuestionType questionType;

    protected abstract String getQuestionString();
    protected abstract QuestionType getQuestionType();
}
