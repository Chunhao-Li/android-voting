package com.example.votingapp.data_storage.firebase_data;


import androidx.annotation.Nullable;

/*Here is QuestionStatistics class which should give statistics data for questionnaire creator,
  for participant-use class you should see non-statistic one.
* */
public abstract class QuestionStatistics extends Question {

    Integer totalVoterCount;
    public abstract Integer getTotalVoterCount();
//    abstract void update(Answer ans);

    @Override
    public boolean equals(@Nullable Object obj) {
        return ((QuestionStatistics) obj).getQuestionString().equals(
                this.getQuestionString()
        );
    }
}
