package com.example.votingapp.backend_storage;

import java.util.ArrayList;

/* Notice: this abstract question class is participant-use class,
   for creator usage you should check QuestionAnalysis class
* */

// abstract question class for any question
abstract class Question{
     QuestionType questionType;
//     question description
     String questionString;
     abstract QuestionType getQuestionType();
     abstract String getQuestionString();
}

//multiple choice question sub class,
class multipleChoiceQuestion extends Question{
//    the name of each class
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

class textQuestion extends Question{

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

//public class Question {
//    private String question;
//    QuestionType type;
//    ArrayList<String> choices;
//
//    public Question(String question, QuestionType type) {
//        this.question = question;
//        this.type = type;
//    }
//}
