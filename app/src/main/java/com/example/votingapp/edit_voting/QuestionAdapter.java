package com.example.votingapp.edit_voting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.data_type.answer.Answer;
import com.example.votingapp.data_type.answer.MultipleChoiceAnswer;
import com.example.votingapp.data_type.answer.TextAnswer;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.data_type.question.QuestionParcel;


import java.util.ArrayList;

public class QuestionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected Context mContext;
    protected LayoutInflater inflater;
    // data
    protected final ArrayList<QuestionParcel> questionItems;
//    private final ArrayList<Answer> answers;

    protected final int TEXT_TYPE = 0;
    protected final int MULTIPLE_CHOICE_TYPE = 1;



    public QuestionAdapter(Context mContext, ArrayList<QuestionParcel> questionItems, ArrayList<Answer> answers) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.questionItems = questionItems;
//        this.answers = answers;
    }

    public QuestionAdapter(Context context, ArrayList<QuestionParcel> data) {
        this.mContext = context;
        this.questionItems = data;
        this.inflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// convert abstract question to view holder
        if (viewType == TEXT_TYPE) {
            View view = inflater.inflate(R.layout.voting_text_question_item, parent, false);
            return new TextQuestionViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.voting_multiple_choice_item, parent, false);
            //TODO
            // Create view holder per multiple choice voting layout

            return new MultipleChoiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextQuestionViewHolder) {
            String question = questionItems.get(position).getQuestionString();
            ((TextQuestionViewHolder) holder).mQuestion.setText(question);

        } else {
            String question =  questionItems.get(position).getQuestionString();
            ((MultipleChoiceViewHolder) holder).mQuestion.setText(question);
            final ArrayList<String> choices = ((MultiChoiceParcel) questionItems.get(position)).getChoices();
//            final ArrayList<CheckBox> checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();
            final CheckBox[] checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();

            for (int i = 0; i < choices.size(); i++) {
                checkBoxes[i].setText(choices.get(i));
                checkBoxes[i].setVisibility(View.VISIBLE);

            }

        }
    }


    @Override
    public int getItemViewType(int position) {
        switch (questionItems.get(position).getQuestionType()) {
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

    protected class TextQuestionViewHolder extends RecyclerView.ViewHolder {
        TextView mQuestion;
        EditText mAns;


        public TextQuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.text_q_question);
            mAns = itemView.findViewById(R.id.text_q_editText);

        }


    }






    protected class MultipleChoiceViewHolder extends RecyclerView.ViewHolder {
        public TextView mQuestion;
        CheckBox mCheckBox0;
        CheckBox mCheckBox1;
        CheckBox mCheckBox2;
        CheckBox mCheckBox3;
        CheckBox mCheckBox4;
        CheckBox mCheckBox5;
        CheckBox mCheckBox6;
        CheckBox mCheckBox7;

        private CheckBox[] getCheckBoxes() {
            return checkBoxes;
        }


        CheckBox[] checkBoxes;

        public MultipleChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.multiple_choice_q);
            mCheckBox0 = itemView.findViewById(R.id.checkBox0);
            mCheckBox1 = itemView.findViewById(R.id.checkBox1);
            mCheckBox2 = itemView.findViewById(R.id.checkBox2);
            mCheckBox3 = itemView.findViewById(R.id.checkBox3);
            mCheckBox4 = itemView.findViewById(R.id.checkBox4);
            mCheckBox5 = itemView.findViewById(R.id.checkBox5);
            mCheckBox6 = itemView.findViewById(R.id.checkBox6);
            mCheckBox7 = itemView.findViewById(R.id.checkBox7);
            checkBoxes = new CheckBox[] {mCheckBox0, mCheckBox1, mCheckBox2, mCheckBox3,
                                mCheckBox4, mCheckBox5, mCheckBox6, mCheckBox7};

        }
    }

//    public String getEditTextData() {
//        String ans =
//    }
}
