package com.example.votingapp.data_type.answer;

import com.example.votingapp.data_type.QuestionType;

public abstract class Answer {
    //    respondent UID
//    protected String respondentUid;
    protected  String questionString;
    protected QuestionType questionType;

//    public abstract String getRespondentUid();
    public abstract String getQuestionString();
    public abstract QuestionType getQuestionType();
}
