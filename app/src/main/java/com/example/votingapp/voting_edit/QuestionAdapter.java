package com.example.votingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.data_storage.voting_edit.TextEditQuestion;

import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    // data
    private final ArrayList<RecyclerViewQuestionItem> questionItems;

    private final int TEXT_TYPE = 0;
    private final int MULTIPLE_CHOICE_TYPE = 1;



    public QuestionAdapter(Context context, ArrayList<RecyclerViewQuestionItem> data) {
        this.mContext = context;
        this.questionItems = data;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (viewType == TEXT_TYPE) {
                View view = inflater.inflate(R.layout.text_question_item, parent, false);
                return new TextQuestionViewHolder(view);
            } else  {
//                View view = inflater
                return null;
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextQuestionViewHolder) {
            String question = ((TextEditQuestion) questionItems.get(position).getData()).getQuestionString();
            ((TextQuestionViewHolder) holder).mQuestion.setText(question);
        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (questionItems.get(position).getType()) {
            case TEXT_QUESTION:
                return TEXT_TYPE;
            case MULTI_CHOICE:
                return MULTIPLE_CHOICE_TYPE;
            default:
                return -1;
        }

    }

    @Override
    public int getItemCount() {
        return questionItems.size();
    }

    class TextQuestionViewHolder extends RecyclerView.ViewHolder{
        TextView mQuestion;
        EditText mAns;

        public TextQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.text_q_question);
            mAns = itemView.findViewById(R.id.text_q_editText);

        }
    }

    class MultipleChoiceViewHolder extends RecyclerView.ViewHolder {
        TextView mQuestion;

        public MultipleChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.multiple_choice_q);
        }
    }
}
