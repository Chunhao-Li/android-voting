package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

public abstract class Answer {
    //    respondent UID
//    protected String respondentUid;
    protected  String questionString;
    protected QuestionType questionType;

//    public abstract String getRespondentUid();
    public abstract String getQuestionString();
    public abstract QuestionType getQuestionType();
}
