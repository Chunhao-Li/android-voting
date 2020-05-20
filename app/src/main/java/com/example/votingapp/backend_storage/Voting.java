package com.example.votingapp.backend_storage;

import java.util.ArrayList;
import java.util.Date;

public class Voting {

//    creator uid of this voting
    private String creatorUid;
//    voting due date
    private Date deadline;

//    main data structure of voting
    private ArrayList<Question> questions;

    Voting(String creatorUid, Date deadline,ArrayList<Question> questions){
        this.creatorUid = creatorUid;
        this.deadline = deadline;
        this.questions = questions;
    }
//    return creator uid
    public String getCreatorUid(){
        return this.creatorUid;
    }
//    return deadline
    public Date getDeadline(){
        return this.deadline;
    }
//    return questions in this voting
    public ArrayList<Question> getQuestions(){
        return this.questions;
    }
}
