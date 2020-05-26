package com.example.votingapp.data_type.answer;


import java.util.ArrayList;

public class UserAnswers {
    //    Voting UID
    private String votingUid;

    private ArrayList<Answer> answers;

    public UserAnswers(String votingUid, ArrayList<Answer> answers) {
        this.votingUid = votingUid;
        this.answers = answers;
    }


    public String getVotingUid() {
        return this.votingUid;
    }

    public ArrayList<Answer> getAnswers() {
        return this.answers;
    }

}
