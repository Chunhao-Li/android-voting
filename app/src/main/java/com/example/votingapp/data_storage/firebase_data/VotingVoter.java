package com.example.votingapp.data_storage.firebase_data;
import com.example.votingapp.voting_edit.EditQuestion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VotingVoter extends Voting {
//    for participant use data structure
    private ArrayList<Question> questions;


    VotingVoter(String creatorUid, String deadline, ArrayList<Question> questions, String title){
        super.VotingUid = Integer.toString(questions.hashCode());
        super.votingTitle = title;
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
    public String getDeadline() {
        return this.deadline;
    }
}
