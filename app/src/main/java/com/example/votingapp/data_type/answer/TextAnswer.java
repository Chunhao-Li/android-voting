package com.example.votingapp.data_type.answer;

import com.example.votingapp.data_type.question.QuestionType;

public class TextAnswer extends Answer {

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    //    string answer for text question
    private String answerText;

    public TextAnswer(String questionString, String answerText) {
        super.questionString = questionString;
        super.questionType = QuestionType.TEXT_QUESTION;
        this.answerText = answerText;
    }


    public String getQuestionTitle() {
        return this.questionString;
    }

    public QuestionType getQuestionType() {
        return this.questionType;
    }

    public String getAnswerString() {
        return this.answerText;
    }
}
