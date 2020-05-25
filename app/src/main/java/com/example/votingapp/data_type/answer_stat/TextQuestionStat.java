package com.example.votingapp.data_type.answer_stat;

import com.example.votingapp.data_type.question.QuestionType;

import java.util.ArrayList;

public class TextQuestionStat extends QuestionStat {
//    a hash map that stores all answer strings with user UID as key
    private ArrayList<String> answers = new ArrayList<>();

//    if it's the first time to create this question, init statistics to default.
    public TextQuestionStat(String question){
    super.questionString = question;
    super.questionType = QuestionType.TEXT_QUESTION;
//    super.totalVoterCount = 0;
    this.answers = new ArrayList<>();
    }

//    inherit previous status
    public TextQuestionStat(String question, String curAns){
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
//        super.totalVoterCount = totalNum;
        if (!curAns.isEmpty()) {
            answers.add(curAns);
        }

    }

    public ArrayList<String> getAnswers(){
        return this.answers;
    }

    @Override
    public Integer getTotalVoterCount() {
        return this.totalVoterCount;
    }

    public void update(String  ans) {
        if (!ans.isEmpty()) {
            this.answers.add(ans);
        }
//        this.totalVoterCount++;
    }

    @Override
    public QuestionType getQuestionType() {
        return this.questionType;
    }

    @Override
    public String getQuestionString() {
        return this.questionString;
    }


}
