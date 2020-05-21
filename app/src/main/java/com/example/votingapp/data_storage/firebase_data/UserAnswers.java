package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

import java.util.ArrayList;

public class UserAnswers {
//    Voting UID
    private String votingUid;
//    respondent UID
    private String respondentUid;

//    main data structure of answers
    ArrayList<Answer> answers;

    UserAnswers(String votingUid, String respondentUid, ArrayList<Answer> answers){
        this.votingUid = votingUid;
        this.respondentUid = respondentUid;
        this.answers = answers;
    }

    public String getRespondentUid(){
        return this.respondentUid;
    };
    public String getVotingUid(){
        return this.votingUid;
    }
    public ArrayList<Answer> getAnswers(){
        return this.answers;
    }

}
