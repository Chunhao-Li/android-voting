package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

public class TextAnswer extends Answer{
//    string answer for text question
    private String answerText;

    TextAnswer(String respondentUid, String questionString, String answerText){
        super.questionString = questionString;
        super.questionType = QuestionType.TEXT_QUESTION;
        this.answerText = answerText;
        super.respondentUid = respondentUid;
    }

    @Override
    protected String getRespondentUid() {
        return this.respondentUid;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    protected String getAnswerString() {
        return this.answerText;
    }
}
