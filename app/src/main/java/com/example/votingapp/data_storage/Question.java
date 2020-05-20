package com.example.votingapp.data_storage;

import android.os.Parcelable;

import java.util.ArrayList;

/* Notice: this abstract question class is participant-use class,
   for creator usage you should check QuestionAnalysis class
* */

// abstract question class for any question
public abstract class Question implements Parcelable {
     QuestionType questionType;
//     question description
     String questionString;
     abstract QuestionType getQuestionType();
     abstract String getQuestionString();
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
