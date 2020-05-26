package com.example.votingapp.do_voting;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.votingapp.edit_voting.QuestionAdapter;

import java.util.ArrayList;

/**
 * This class is for showing the questions when users do a voting, and will save the
 * answer for saving on the cloud later.
 */
public class DoVotingAdapter extends QuestionAdapter {

    private final ArrayList<Answer> answers;

    DoVotingAdapter(Context mContext, ArrayList<QuestionParcel> questionItems, ArrayList<Answer> answers) {
        super(mContext, questionItems);
        this.answers = answers;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TEXT_TYPE) {
            View view = inflater.inflate(R.layout.voting_text_question_item, parent, false);
            return new TextQuestionViewHolder(view, new TextAnswerListener());
        } else {
            View view = inflater.inflate(R.layout.voting_multiple_choice_item, parent, false);
            return new MultipleChoiceViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof TextQuestionViewHolder) {
            // set question title and update position for the listener
            String question = questionItems.get(position).getQuestionTitle();
            ((TextQuestionViewHolder) holder).mQuestion.setText(question);
            ((TextQuestionViewHolder) holder).listener.updatePosition(holder.getAdapterPosition());

        } else {
            // set title and choices for the multiChoice problem, and update position
            String question = questionItems.get(position).getQuestionTitle();
            ((MultipleChoiceViewHolder) holder).mQuestion.setText(question);
            final ArrayList<String> choices = ((MultiChoiceParcel) questionItems.get(position)).getChoices();
            final CheckBox[] checkBoxes = ((MultipleChoiceViewHolder) holder).getCheckBoxes();
            final MultiChoiceRecorder[] recorders = ((MultipleChoiceViewHolder) holder).getRecorders();

            // show the available checkboxes and update positions
            for (int i = 0; i < choices.size(); i++) {
                checkBoxes[i].setText(choices.get(i));
                checkBoxes[i].setVisibility(View.VISIBLE);
                recorders[i].updatePosition(position, i);
            }
        }
    }

    class TextAnswerListener implements TextWatcher {
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

    protected class TextQuestionViewHolder extends QuestionAdapter.TextQuestionViewHolder {
        TextView mQuestion;
        EditText mAns;
        TextAnswerListener listener;

        TextQuestionViewHolder(@NonNull View itemView, TextAnswerListener listener) {
            super(itemView);
            mQuestion = itemView.findViewById(R.id.text_q_question);
            mAns = itemView.findViewById(R.id.editText_text_title);
            this.listener = listener;
            mAns.addTextChangedListener(listener);
        }
    }


    private class MultiChoiceRecorder implements CompoundButton.OnCheckedChangeListener {
        private int checkboxId;
        private int questionPos;    // position of the question
        private int choicePos;  // position of the choice

        private void updatePosition(int questionPos, int choicePos) {
            this.questionPos = questionPos;
            this.choicePos = choicePos;
        }

        MultiChoiceRecorder(int checkboxId) {
            this.checkboxId = checkboxId;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // save answer
            if (buttonView.getId() == checkboxId) {
                MultipleChoiceAnswer choiceAnswer = (MultipleChoiceAnswer) answers.get(questionPos);
                if (isChecked) {
                    choiceAnswer.getAnswerChoice().get(choicePos).set(1, "1");
                } else {
                    choiceAnswer.getAnswerChoice().get(choicePos).set(1, "0");
                }
            }
        }
    }

    class MultipleChoiceViewHolder extends QuestionAdapter.MultipleChoiceViewHolder {
        MultiChoiceRecorder[] recorders;


        private MultiChoiceRecorder[] getRecorders() {
            return recorders;
        }

        MultipleChoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            recorders = new MultiChoiceRecorder[]{new MultiChoiceRecorder(R.id.checkBox0),
                    new MultiChoiceRecorder(R.id.checkBox1), new MultiChoiceRecorder(R.id.checkBox2),
                    new MultiChoiceRecorder(R.id.checkBox3), new MultiChoiceRecorder(R.id.checkBox4),
                    new MultiChoiceRecorder(R.id.checkBox5), new MultiChoiceRecorder(R.id.checkBox6),
                    new MultiChoiceRecorder(R.id.checkBox7)};
            for (int i = 0; i < checkBoxes.length; i++) {
                checkBoxes[i].setOnCheckedChangeListener(recorders[i]);
            }
        }
    }
}
