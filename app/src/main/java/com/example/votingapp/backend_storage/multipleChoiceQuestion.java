package com.example.votingapp.backend_storage;

import java.util.ArrayList;

//multiple choice question sub class,
public class multipleChoiceQuestion extends Question{
//    the name of each choice
    private ArrayList<String> choices;

    public multipleChoiceQuestion(String question, ArrayList<String> choices){
        super.questionString = question;
        super.questionType = QuestionType.MULTI_CHOICE;
        this.choices = choices;
    }

    public ArrayList<String> getChoices(){
        return this.choices;
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
