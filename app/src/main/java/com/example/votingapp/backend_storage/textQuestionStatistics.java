package com.example.votingapp.backend_storage;

import java.util.HashMap;
import java.util.Map;

public class textQuestionStatistics extends QuestionStatistics{
//    a hash map that stores all answer strings with user UID as key
    private HashMap<String,String> answers;

    public textQuestionStatistics(String question, Integer totalNum, HashMap<String,String> answers){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
        super.totalVoterCount = totalNum;
        this.answers = answers;
    }

    public HashMap<String,String> getAnwsers(){
        return this.answers;
    }

    @Override
    public Integer getTotalVoterCount() {
        return this.totalVoterCount;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }
}
