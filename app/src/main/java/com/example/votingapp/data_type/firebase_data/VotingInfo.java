package com.example.votingapp.data_type.firebase_data;

import com.example.votingapp.voting_edit.QuestionParcel;

import java.util.ArrayList;

public class VotingInfo extends Voting {
//    for creator only main structure
    private ArrayList<QuestionParcel> questions;



//      the result of each question has been stored in questionStatistics class.
    public VotingInfo(String creatorUid, String deadline, ArrayList<QuestionParcel> questions, String title) {
        super.VotingUid = Integer.toString(questions.hashCode());
        super.creatorUid = creatorUid;
        super.deadline = deadline;
        super.votingTitle = title;
        this.questions =questions;

    }

//    public void addAnswers(UserAnswers answers){
//        for(int i = 0;i<answers.getAnswers().size();i++){
//            if (answers.getAnswers().get(i).questionType.equals(QuestionType.MULTI_CHOICE)){
//                this.questionStatistics.get(i).update(answers.getAnswers().get(i));
//            }else if(answers.getAnswers().get(i).questionType.equals(QuestionType.TEXT_QUESTION)){
//                this.questionStatistics.get(i).update(answers.getAnswers().get(i));
//            }
//        }
//    }

//    if you want the whole data structure
    public ArrayList<QuestionParcel> getQuestions(){
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
