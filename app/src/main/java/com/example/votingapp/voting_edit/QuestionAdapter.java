package com.example.votingapp.voting_edit;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.MultiChoiceQuestion;
import com.example.votingapp.R;


import java.util.ArrayList;
import java.util.Dictionary;

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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// convert abstract question to view holder
            if (viewType == TEXT_TYPE) {
                View view = inflater.inflate(R.layout.voting_text_question_item, parent, false);
                return new TextQuestionViewHolder(view);
            } else  {
                View view = inflater.inflate(R.layout.voting_multiple_choice_item,parent,false);
                //TODO
                // Create view holder per multiple choice voting layout

                return new MultipleChoiceViewHolder(view);
            }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextQuestionViewHolder) {
            String question = ((EditTextQuestion) questionItems.get(position).getData()).getQuestionString();
            ((TextQuestionViewHolder) holder).mQuestion.setText(question);
        }else{
            String question = ((EditMultiChoiceQuestion) questionItems.get(position).getData()).getQuestionString();
            ((MultipleChoiceViewHolder) holder).mQuestion.setText(question);
            ArrayList<String> choices= ((EditMultiChoiceQuestion) questionItems.get(position).getData()).getChoices();
            ArrayList<CheckBox> checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();
            for (int i = 0; i < choices.size(); i++) {
                checkBoxes.get(i).setText(choices.get(i));
                checkBoxes.get(i).setVisibility(View.VISIBLE);
            }

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
        CheckBox mCheckBox0;
        CheckBox mCheckBox1;
        CheckBox mCheckBox2;
        CheckBox mCheckBox3;
        CheckBox mCheckBox4;
        CheckBox mCheckBox5;
        CheckBox mCheckBox6;
        CheckBox mCheckBox7;

        public ArrayList<CheckBox> getCheckBoxes() {
            return checkBoxes;
        }

        ArrayList<CheckBox> checkBoxes = new ArrayList<>();

        //TODO

        /**
         * Defined in viewing layout
         * @param itemView
         */


        public MultipleChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.multiple_choice_q);
            mCheckBox0= itemView.findViewById(R.id.checkBox0);
            mCheckBox1= itemView.findViewById(R.id.checkBox1);
            mCheckBox2= itemView.findViewById(R.id.checkBox2);
            mCheckBox3= itemView.findViewById(R.id.checkBox3);
            mCheckBox4= itemView.findViewById(R.id.checkBox4);
            mCheckBox5= itemView.findViewById(R.id.checkBox5);
            mCheckBox6= itemView.findViewById(R.id.checkBox6);
            mCheckBox7= itemView.findViewById(R.id.checkBox7);
            checkBoxes.add(mCheckBox0);
            checkBoxes.add(mCheckBox1);
            checkBoxes.add(mCheckBox2);
            checkBoxes.add(mCheckBox3);
            checkBoxes.add(mCheckBox4);
            checkBoxes.add(mCheckBox5);
            checkBoxes.add(mCheckBox6);
            checkBoxes.add(mCheckBox7);
        }
    }
}
