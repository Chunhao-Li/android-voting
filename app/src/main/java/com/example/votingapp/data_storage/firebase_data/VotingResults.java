package com.example.votingapp.data_storage.firebase_data;

import com.example.votingapp.data_storage.QuestionType;

import java.util.ArrayList;
import java.util.Date;

public class VotingResults extends Voting {
//    for creator only main structure
    private ArrayList<QuestionStatistics> questionStatistics;
//      the result of each question has been stored in questionStatistics class.
    VotingResults(String creatorUid, Date deadline, ArrayList<QuestionStatistics> questions) {
        super.VotingUid = Integer.toString(questions.hashCode());
        super.creatorUid = creatorUid;
        super.deadline = deadline;
        this.questionStatistics =questions;
    }

    public void addAnswers(UserAnswers answers){
        for(int i = 0;i<answers.getAnswers().size();i++){
            if (answers.getAnswers().get(i).questionType.equals(QuestionType.MULTI_CHOICE)){
                this.questionStatistics.get(i).update(answers.getAnswers().get(i));
            }else if(answers.getAnswers().get(i).questionType.equals(QuestionType.TEXT_QUESTION)){
                this.questionStatistics.get(i).update(answers.getAnswers().get(i));
            }
        }
    }

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
