package com.example.votingapp.backend_storage;

import java.util.ArrayList;

/* Notice: this abstract question class is participant-use class,
   for creator usage you should check QuestionStatistics class with further extension
* */

// abstract question class for any question
abstract class Question{
     QuestionType questionType;
//     question description
     String questionString;
     abstract QuestionType getQuestionType();
     abstract String getQuestionString();
}

/*Here is QuestionStatistics class which should give statistics data for questionnaire creator,
  for participant-use class you should see non-statistic one.
* */
abstract class QuestionStatistics extends Question{
//    count the # of voters
    Integer totalVoterCount;
    abstract Integer getTotalVoterCount();
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
