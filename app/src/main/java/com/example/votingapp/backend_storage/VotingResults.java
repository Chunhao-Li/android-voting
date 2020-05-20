package com.example.votingapp.backend_storage;

import java.util.ArrayList;
import java.util.Date;

public class VotingResults extends Voting{
//    for creator only main structure
    private ArrayList<QuestionStatistics> questionStatistics = new ArrayList<>();
//      the result of each question has been stored in questionStatistics class.
    VotingResults(String creatorUid, Date deadline, ArrayList<QuestionStatistics> questions) {
        super.VotingUid = Integer.toString(questions.hashCode());
        super.creatorUid = creatorUid;
        super.deadline = deadline;
        this.questionStatistics =questions;
    }

//    public void addAnswers()

//    if you want the whole data structure
    public ArrayList<QuestionStatistics> getQuestionStatistics(){
        return this.questionStatistics;
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
