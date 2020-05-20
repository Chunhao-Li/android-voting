package com.example.votingapp.backend_storage;

public class textQuestion extends Question{

    public textQuestion(String question){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
    }

    @Override
    public QuestionType getQuestionType(){
        return super.questionType;
    }

    @Override
    public String getQuestionString(){
        return super.questionString;
    }
}
