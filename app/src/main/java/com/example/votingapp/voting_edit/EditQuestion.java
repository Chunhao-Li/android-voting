package com.example.votingapp.voting_edit;

import android.os.Parcelable;

import com.example.votingapp.data_storage.firebase_data.Question;

/* Notice: this abstract question class is participant-use class,
   for creator usage you should check QuestionAnalysis class
* */

// abstract question class for any question
public abstract class EditQuestion extends Question implements Parcelable {

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