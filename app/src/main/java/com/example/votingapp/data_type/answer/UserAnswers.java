package com.example.votingapp.data_type.answer;

import com.example.votingapp.data_type.answer.Answer;

import java.util.ArrayList;

public class UserAnswers {
//    Voting UID
    private String votingUid;
////    respondent UID
//    private String respondentUid;

//    main data structure of answers
    private ArrayList<Answer> answers;

    public UserAnswers(String votingUid, ArrayList<Answer> answers){
        this.votingUid = votingUid;
//        this.respondentUid = respondentUid;
        this.answers = answers;
    }

//    public String getRespondentUid(){
//        return this.respondentUid;
//    };
    public String getVotingUid(){
        return this.votingUid;
    }
    public ArrayList<Answer> getAnswers(){
        return this.answers;
    }

}
