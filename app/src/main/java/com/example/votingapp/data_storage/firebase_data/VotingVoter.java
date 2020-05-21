package com.example.votingapp.data_storage.firebase_data;
import com.example.votingapp.voting_edit.EditQuestion;

import java.util.ArrayList;
import java.util.Date;

public class VotingVoter extends Voting {
//    for participant use data structure
    private ArrayList<Question> questions;


    VotingVoter(String creatorUid, Date deadline, ArrayList<Question> questions){
        super.VotingUid = Integer.toString(questions.hashCode());
        this.creatorUid = creatorUid;
        this.deadline = deadline;
        this.questions = questions;
    }

    public ArrayList<Question> getQuestions(){
        return this.questions;
    }


    @Override
    String getVotingUid() {
        return this.VotingUid;
    }

    @Override
    public String getCreatorUid() {
        return this.creatorUid;
    }

    @Override
    public Date getDeadline() {
        return this.deadline;
    }
}
