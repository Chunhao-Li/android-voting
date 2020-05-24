package com.example.votingapp.voting_result;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.voting_edit.EditMultiChoiceQuestion;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;

import java.util.ArrayList;

class InnerAdapter extends RecyclerView.Adapter {
//    private final ArrayList<RecyclerViewQuestionItem> questionItems;
    private final EditMultiChoiceQuestion question;

    public InnerAdapter(EditMultiChoiceQuestion multiChoiceQuestion) {
        this.question = multiChoiceQuestion;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerMultiChoiceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_multi_choice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
//        ((InnerMultiChoiceHolder )holder).choiceText.setText("item" + position);
//        ((InnerMultiChoiceHolder )holder).choiceCount.setText("ans" + position);
        ArrayList<String> choices = question.getChoices();
        for (int i = 0; i < choices.size(); i++) {
            ((InnerMultiChoiceHolder )holder).choiceText.setText(choices.get(i));
            ((InnerMultiChoiceHolder )holder).choiceCount.setText("0");
            Log.d("innerappder,", "here:"+Integer.toString(choices.size()));
        }

    }

    @Override
    public int getItemCount() {
        return question.getChoices().size();
    }

    public static class InnerMultiChoiceHolder extends RecyclerView.ViewHolder {
        private TextView choiceText;
        private TextView choiceCount;

        public InnerMultiChoiceHolder(@NonNull View itemView) {
            super(itemView);
            choiceText = itemView.findViewById(R.id.inner_choice_text);
            choiceCount = itemView.findViewById(R.id.inner_choice_count);
        }
    }


}
