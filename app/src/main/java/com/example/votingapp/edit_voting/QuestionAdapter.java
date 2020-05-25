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
    private final ArrayList<Answer> answers;

    protected final int TEXT_TYPE = 0;
    protected final int MULTIPLE_CHOICE_TYPE = 1;



//    protected QuestionAdapter(Context mContext) {
//        this.mContext = mContext;
//        this.inflater = LayoutInflater.from(mContext);
//        questionItems = new ArrayList<>();
//        answers = new ArrayList<>();
//    }
//

    public QuestionAdapter(Context mContext, ArrayList<QuestionParcel> questionItems, ArrayList<Answer> answers) {
        this.mContext = mContext;
        this.inflater = LayoutInflater.from(mContext);
        this.questionItems = questionItems;
        this.answers = answers;
    }

    public QuestionAdapter(Context context, ArrayList<QuestionParcel> data) {
        this.mContext = context;
        this.questionItems = data;
        this.inflater = LayoutInflater.from(mContext);
        this.answers = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {// convert abstract question to view holder
        if (viewType == TEXT_TYPE) {
            View view = inflater.inflate(R.layout.voting_text_question_item, parent, false);
            return new TextQuestionViewHolder(view, new TextAnswerListener());
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
            ((TextQuestionViewHolder) holder).listener.updatePosition(holder.getAdapterPosition());

        } else {
            String question =  questionItems.get(position).getQuestionString();
            ((MultipleChoiceViewHolder) holder).mQuestion.setText(question);
            final ArrayList<String> choices = ((MultiChoiceParcel) questionItems.get(position)).getChoices();
//            final ArrayList<CheckBox> checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();
            final CheckBox[] checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();
            final MultiChoiceRecorder[] recorders =   ((MultipleChoiceViewHolder) holder).getRecorders();
//            int checkBoxCheckedCounter=0;


//            for (int i = 0; i < recorders.length; i++) {
//                recorders[i].updatePosition(position);
//            }
            for (int i = 0; i < choices.size(); i++) {
                checkBoxes[i].setText(choices.get(i));
                checkBoxes[i].setVisibility(View.VISIBLE);
                recorders[i].updatePosition(position, i);
//                if(checkBoxes[i].isChecked()) {
//                    checkBoxCheckedCounter++;
//                }
//                recorders[i].updatePosition();
            }

            /**
             * This OnClickListener works to determine whether a box is selected and record the state
             */
//            MultiChoiceRecorder multiChoiceRecorder = new MultiChoiceRecorder();
//            if(checkBoxCheckedCounter>0){
//                for(int i=0;i<checkBoxes.size();i++){
//                    multiChoiceRecorder.RecordChoice(choices.get(i),checkBoxes.get(i));
//                }
//                MultipleChoiceAnswer multipleChoiceAnswer = new MultipleChoiceAnswer(question, multiChoiceRecorder.multiChoiceAnswer);
//                answers.add(multipleChoiceAnswer);
//            }


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
        TextAnswerListener listener;

        public TextQuestionViewHolder(@NonNull View itemView, TextAnswerListener listener) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.text_q_question);
            mAns = itemView.findViewById(R.id.text_q_editText);
            this.listener = listener;
            mAns.addTextChangedListener(listener);

        }


    }

    private class TextAnswerListener implements TextWatcher {
        int position;   // position of the ViewHolder

        private void updatePosition(int pos) {
            this.position = pos;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            TextAnswer textAnswer = (TextAnswer) answers.get(position);
            textAnswer.setAnswerText(s.toString());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class MultiChoiceRecorder implements CompoundButton.OnCheckedChangeListener {
        ArrayList<ArrayList<String>> multiChoiceAnswer;
        ArrayList<String> singleChoice;
        private int checkboxItem;
        private int questionPos;
        private int choicePos;

        private void updatePosition(int questionPos, int choicePos) {
            this.questionPos = questionPos;
            this.choicePos = choicePos;
        }

        public MultiChoiceRecorder(int checkboxItem) {
            this.checkboxItem = checkboxItem;
        }

        public void RecordChoice(String choice, CheckBox checkBox){

            singleChoice.add(choice);

            if(checkBox.isChecked()){
                singleChoice.add("1");
            }else singleChoice.add("0");

            multiChoiceAnswer.add(singleChoice);
            singleChoice.clear();
        }


        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == checkboxItem) {
                MultipleChoiceAnswer choiceAnswer = (MultipleChoiceAnswer) answers.get(questionPos);
                if (isChecked) {

                    choiceAnswer.getAnswerChoice().get(choicePos).set(1, "1");
                } else {
                    choiceAnswer.getAnswerChoice().get(choicePos).set(1, "0");
                }
//

            }

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

        private CheckBox[] getCheckBoxes() {
            return checkBoxes;
        }

        private MultiChoiceRecorder[] getRecorders() {
            return recorders;
        }

        //        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        CheckBox[] checkBoxes;
        MultiChoiceRecorder[] recorders;

        //TODO

        /**
         * Defined in viewing layout
         *
         * @param itemView
         */


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
            recorders = new MultiChoiceRecorder[] {new MultiChoiceRecorder(R.id.checkBox0),
                    new MultiChoiceRecorder(R.id.checkBox1), new MultiChoiceRecorder(R.id.checkBox2),
                    new MultiChoiceRecorder(R.id.checkBox3),new MultiChoiceRecorder(R.id.checkBox4),
                    new MultiChoiceRecorder(R.id.checkBox5),new MultiChoiceRecorder(R.id.checkBox6),
                    new MultiChoiceRecorder(R.id.checkBox7)};
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setOnCheckedChangeListener(recorders[i]);
            }
        }
    }

//    public String getEditTextData() {
//        String ans =
//    }
}
