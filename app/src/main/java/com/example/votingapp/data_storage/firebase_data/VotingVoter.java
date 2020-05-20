package com.example.votingapp.data_storage;
import com.example.votingapp.data_storage.voting_edit.EditQuestion;

import java.util.ArrayList;
import java.util.Date;

public class VotingVoter extends Voting {
//    for participant use data structure
    private ArrayList<EditQuestion> questions;
//    participant ID
    private String participantUid;

    VotingVoter(String participantUid, String creatorUid, Date deadline, ArrayList<EditQuestion> questions){
        super.VotingUid = Integer.toString(questions.hashCode());
        this.creatorUid = creatorUid;
        this.deadline = deadline;
        this.questions = questions;
        this.participantUid = participantUid;
    }

    public ArrayList<EditQuestion> getQuestions(){
        return this.questions;
    }

    public String getParticipantID(){
        return this.participantUid;
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
