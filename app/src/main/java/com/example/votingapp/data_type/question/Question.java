package com.example.votingapp.data_type.question;

import com.example.votingapp.data_type.question.QuestionType;

public abstract class Question {
    protected QuestionType questionType;
    //     question title
    protected String questionString;

    public abstract QuestionType getQuestionType();

    public abstract String getQuestionTitle();
}
