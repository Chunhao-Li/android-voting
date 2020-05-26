package com.example.votingapp.data_type.answer;

import com.example.votingapp.data_type.question.QuestionType;

public abstract class Answer {
    //    protected String respondentUid;
    protected String questionString;
    protected QuestionType questionType;

    //    public abstract String getRespondentUid();
    public abstract String getQuestionString();

    public abstract QuestionType getQuestionType();
}
