package com.example.votingapp.data_type.question;

import android.os.Parcelable;

/* Notice: this abstract question class is participant-use class,
   for creator usage you should check QuestionAnalysis class
* */

// abstract question class for any question
public abstract class QuestionParcel extends Question implements Parcelable {

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
