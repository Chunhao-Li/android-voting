package com.example.votingapp.voting_result;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.data_storage.firebase_data.Answer;
import com.example.votingapp.data_storage.firebase_data.TextAnswer;
import com.example.votingapp.voting_edit.EditMultiChoiceQuestion;
import com.example.votingapp.voting_edit.EditTextQuestion;
import com.example.votingapp.voting_edit.QuestionAdapter;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;

import java.util.ArrayList;

public class ResultAdapter extends QuestionAdapter {

    private ArrayList<ArrayList<Answer>> answers;
//    private ArrayList<RecyclerViewQuestionItem> questionItems;


    public ResultAdapter(Context mContext, ArrayList<RecyclerViewQuestionItem> questionItems, ArrayList<ArrayList<Answer>> answers) {
        super(mContext, questionItems);
        this.answers = answers;
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
                    R.layout.inner_viewholder_item, parent, false));
//            View view = inflater.inflate(R.layout.result_multi_choice_item, parent, false);
//            return new MultiQuestionViewHolder(view);

//            return new MultipleChoiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextQuestionViewHolder) {
            String question = questionItems.get(position).getData().getQuestionString();
            ((TextQuestionViewHolder) holder).questionTitle.setText(question);

        }else{

            EditMultiChoiceQuestion question = (EditMultiChoiceQuestion) questionItems.get(position).getData();
            Log.d("ResultAdapter:", Integer.toString(question.getChoices().size()));
               ((InnerViewHolder) holder).questionTitle.setText(question.getQuestionString());
            ((InnerViewHolder) holder).recyclerView.setAdapter(new InnerAdapter(question));
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
            this.questionTitle = itemView.findViewById(R.id.text_question);
            this.ansDetail = itemView.findViewById(R.id.button_text_detail);
        }
    }

    class MultiQuestionViewHolder extends RecyclerView.ViewHolder {
        private TextView questionTitle;
        private Button ansDetail;

        public MultiQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            this.questionTitle = itemView.findViewById(R.id.multi_choice_question);
            this.ansDetail = itemView.findViewById(R.id.button_multi_choice_detail);
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

        public void bindData(EditMultiChoiceQuestion question) {
            InnerAdapter innerAdapter = new InnerAdapter(question);
            recyclerView.setAdapter(innerAdapter);
            innerAdapter.notifyDataSetChanged();
        }

    }



}
