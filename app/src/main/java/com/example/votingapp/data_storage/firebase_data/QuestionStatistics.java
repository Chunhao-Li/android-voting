package com.example.votingapp.data_storage;


import com.example.votingapp.data_storage.voting_edit.EditQuestion;

/*Here is QuestionStatistics class which should give statistics data for questionnaire creator,
  for participant-use class you should see non-statistic one.
* */
public abstract class QuestionStatistics extends EditQuestion {

    Integer totalVoterCount;
    abstract Integer getTotalVoterCount();
    abstract QuestionType getQuestionType();
    abstract String getQuestionString();
}
