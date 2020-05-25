package com.example.votingapp.data_type.answer_stat;

import com.example.votingapp.data_type.question.QuestionType;

import java.util.ArrayList;

public class TextQuestionStat extends QuestionStat {
    //    a hash map that stores all answer strings with user UID as key
    private ArrayList<String> answers = new ArrayList<>();


    //    inherit previous status
    public TextQuestionStat(String question, String curAns) {
        super.questionString = question;
        super.questionType = QuestionType.TEXT_QUESTION;
        if (!curAns.isEmpty()) {
            answers.add(curAns);
        }

    }

    public ArrayList<String> getAnswers() {
        return this.answers;
    }


    public void update(String ans) {
        if (!ans.isEmpty()) {
            this.answers.add(ans);
        }
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
