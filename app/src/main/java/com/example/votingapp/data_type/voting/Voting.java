package com.example.votingapp.data_type.voting;

import com.example.votingapp.data_type.question.QuestionParcel;

import java.util.ArrayList;

public class Voting {
//    for creator only main structure
    private ArrayList<QuestionParcel> questions;
    //    Voting UID
    private String VotingUid;
    //    creator uid of this voting
    private String creatorUid;
    //    voting due date
    private String deadline; //  year/month/day/hour/minute
    // voting title
    private String votingTitle;
    public String getVotingTitle() {
        return votingTitle;
    }



//      the result of each question has been stored in questionStatistics class.
    public Voting(String creatorUid, String deadline, ArrayList<QuestionParcel> questions, String title) {
        this.VotingUid = Integer.toString(questions.hashCode());
        this.creatorUid = creatorUid;
        this.deadline = deadline;
        this.votingTitle = title;
        this.questions =questions;

    }

//    if you want the whole data structure
    public ArrayList<QuestionParcel> getQuestions(){
        return this.questions;
    }


    public String getCreatorUid() {
        return this.creatorUid;
    }

    public String getDeadline() {
        return this.deadline;
    }
}
