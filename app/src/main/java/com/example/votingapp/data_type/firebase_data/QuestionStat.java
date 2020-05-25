package com.example.votingapp.data_type.firebase_data;


import androidx.annotation.Nullable;

/*Here is QuestionStatistics class which should give statistics data for questionnaire creator,
  for participant-use class you should see non-statistic one.
* */
public abstract class QuestionStat extends Question {

    Integer totalVoterCount;
    public abstract Integer getTotalVoterCount();
//    abstract void update(Answer ans);

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((QuestionStat) obj).getQuestionString().equals(
                this.getQuestionString()
        );
    }
}
