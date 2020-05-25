package com.example.votingapp.voting_result;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.data_storage.firebase_data.MultiChoiceQuestStat;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;
import com.example.votingapp.data_storage.firebase_data.TextQuestionStatistics;
import com.example.votingapp.voting_edit.EditMultiChoiceQuestion;
import com.example.votingapp.voting_edit.QuestionAdapter;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;

import java.util.ArrayList;

public class ResultAdapter extends QuestionAdapter {

//    private ArrayList<ArrayList<Answer>> answers;
    private ArrayList<QuestionStatistics> questionStatistics;
//    private ArrayList<RecyclerViewQuestionItem> questionItems;

    public static final String GET_TEXT_STAT = "com.example.votingapp.voting_result.TEXT_STAT";
    public static final String GET_TEXT_COUNT = "com.example.votingapp.voting_result.TEXT_COUNT";


    public ResultAdapter(Context mContext, ArrayList<RecyclerViewQuestionItem> questionItems,
                         ArrayList<QuestionStatistics> questionStatistics) {
        super(mContext, questionItems);
        this.questionStatistics = questionStatistics;
    }

//    public ResultAdapter(Context context, ArrayList<RecyclerViewQuestionItem> data) {
//        super(context, data);
//    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TEXT_TYPE) {
            View view = inflater.inflate(R.layout.result_text_question_item, parent, false);
            return new TextQuestionViewHolder(view);
        } else  {
            //TODO
            // Create view holder per multiple choice voting layout

            return  new InnerViewHolder(inflater.inflate(
                    R.layout.result_multi_choice_item, parent, false));
//            View view = inflater.inflate(R.layout.result_multi_choice_item, parent, false);
//            return new MultiQuestionViewHolder(view);

//            return new MultipleChoiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TextQuestionViewHolder) {
            String question = questionItems.get(position).getData().getQuestionString();
            ((TextQuestionViewHolder) holder).questionTitle.setText(question);
            ((TextQuestionViewHolder) holder).ansDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TextResultDetailActivity.class);
                    intent.putStringArrayListExtra(GET_TEXT_STAT,
                            ((TextQuestionStatistics) questionStatistics.get(position)).getAnswers());
//                    intent.putExtra(GET_TEXT_COUNT, questionStatistics.get(position).getTotalVoterCount());
                    mContext.startActivity(intent);
                }
            });

        }else{

            EditMultiChoiceQuestion question = (EditMultiChoiceQuestion) questionItems.get(position).getData();
            Log.d("ResultAdapter:", Integer.toString(question.getChoices().size()));
               ((InnerViewHolder) holder).questionTitle.setText(question.getQuestionString());
               ArrayList<Integer> choiceCount = ((MultiChoiceQuestStat) questionStatistics.get(position)).getChoiceVoterCount();
            ((InnerViewHolder) holder).recyclerView.setAdapter(new InnerAdapter(question, choiceCount));
//            String question = questionItems.get(position).getData().getQuestionString();
//            ((MultiQuestionViewHolder) holder).questionTitle.setText(question);
        }

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    class TextQuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTitle;
        private Button ansDetail;

        public TextQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.questionTitle = itemView.findViewById(R.id.result_text_q_title);
            this.ansDetail = itemView.findViewById(R.id.button_text_detail);

        }
    }


    class InnerViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView recyclerView;
        private EditMultiChoiceQuestion question;
         private TextView questionTitle;

        public InnerViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitle = itemView.findViewById(R.id.result_multi_title);
            recyclerView = itemView.findViewById(R.id.inner_recyclerview);
//            this.question = question;
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL, false));
//            recyclerView.setAdapter(new InnerAdapter(question));
        }

//        public void bindData(EditMultiChoiceQuestion question) {
//            InnerAdapter innerAdapter = new InnerAdapter(question);
//            recyclerView.setAdapter(innerAdapter);
//            innerAdapter.notifyDataSetChanged();
//        }

    }



}
