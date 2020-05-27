package com.example.votingapp.data_type.answer_stat;


import androidx.annotation.Nullable;

import com.example.votingapp.data_type.answer.Answer;
import com.example.votingapp.data_type.question.Question;

/*Here is QuestionStatistics class which should give statistics data for questionnaire creator,
  for participant-use class you should see non-statistic one.
* */
public abstract class AnswerStat extends Answer {

    @Override
    public boolean equals(@Nullable Object obj) {
        assert obj != null;
        return ((AnswerStat) obj).getQuestionTitle().equals(
                this.getQuestionTitle()
        );
    }
}
