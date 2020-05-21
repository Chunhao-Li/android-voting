package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

abstract class Answer {
    //    respondent UID
    protected String respondentUid;
    protected  String questionString;
    protected QuestionType questionType;

    protected  abstract String getRespondentUid();
    protected abstract String getQuestionString();
    protected abstract QuestionType getQuestionType();
    protected abstract String getAnswerString();
}
