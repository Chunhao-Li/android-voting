package com.example.votingapp.view_voting;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.data_type.answer_stat.MultiChoiceStat;
import com.example.votingapp.data_type.answer_stat.QuestionStat;
import com.example.votingapp.data_type.answer_stat.TextQuestionStat;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.edit_voting.QuestionAdapter;
import com.example.votingapp.data_type.question.QuestionParcel;

import java.util.ArrayList;

/**
 * This adapter is for showing the stat of a multi choice question in the VotingResultActivity
 */
public class ResultAdapter extends QuestionAdapter {


    private ArrayList<QuestionStat> questionStatistics;

    static final String GET_TEXT_STAT = "com.example.votingapp.voting_result.TEXT_STAT";


    ResultAdapter(Context mContext, ArrayList<QuestionParcel> questionItems,
                  ArrayList<QuestionStat> questionStatistics) {
        super(mContext, questionItems);
        this.questionStatistics = questionStatistics;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TEXT_TYPE) {
            View view = inflater.inflate(R.layout.result_text_question_item, parent, false);
            return new TextQuestionViewHolder(view);
        } else {
            return new MultiChoiceStatHolder(inflater.inflate(
                    R.layout.result_multi_choice_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextQuestionViewHolder) {
            // show the question title and initialize the "View Detail" button
            String question = questionItems.get(position).getQuestionTitle();
            ((TextQuestionViewHolder) holder).questionTitle.setText(question);
            ((TextQuestionViewHolder) holder).ansDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TextResultDetailActivity.class);
                    intent.putStringArrayListExtra(GET_TEXT_STAT,
                            ((TextQuestionStat) questionStatistics.get(position)).getAnswers());
                    mContext.startActivity(intent);
                }
            });

        } else {
            // show the question title and choices of the multi choice question,
            // and then the inner recycler view will show the stat.
            MultiChoiceParcel question = (MultiChoiceParcel) questionItems.get(position);
            ((MultiChoiceStatHolder) holder).questionTitle.setText(question.getQuestionTitle());
            ArrayList<Integer> choiceCount = ((MultiChoiceStat) questionStatistics.get(position)).getChoiceVoterCount();
            ((MultiChoiceStatHolder) holder).recyclerView.setAdapter(new InnerAdapter(question, choiceCount));

        }

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    static class TextQuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTitle;
        private Button ansDetail;

        TextQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.questionTitle = itemView.findViewById(R.id.result_text_q_title);
            this.ansDetail = itemView.findViewById(R.id.button_text_detail);
        }
    }


    static class MultiChoiceStatHolder extends RecyclerView.ViewHolder {
        // This holder contains a recycler view for showing the stat
        private RecyclerView recyclerView;
        private TextView questionTitle;

        MultiChoiceStatHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.result_multi_title);
            recyclerView = itemView.findViewById(R.id.inner_recyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),
                    LinearLayoutManager.VERTICAL, false));
        }
    }
}
