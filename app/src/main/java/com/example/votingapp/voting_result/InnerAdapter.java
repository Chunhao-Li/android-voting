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

import java.util.ArrayList;

class InnerAdapter extends RecyclerView.Adapter {
//    private final ArrayList<RecyclerViewQuestionItem> questionItems;
    private final EditMultiChoiceQuestion question;
    private final ArrayList<Integer> choiceCount;

    public InnerAdapter(EditMultiChoiceQuestion multiChoiceQuestion, ArrayList<Integer> choiceCount) {
        this.question = multiChoiceQuestion;
        this.choiceCount = choiceCount;
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
        ((InnerMultiChoiceHolder )holder).choiceText.setText(choices.get(position));
        String countText= Integer.toString(choiceCount.get(position));
        ((InnerMultiChoiceHolder )holder).choiceCount.setText(countText);
//        for (int i = 0; i < choices.size(); i++) {
//            ((InnerMultiChoiceHolder )holder).choiceText.setText(choices.get(i));
//            ((InnerMultiChoiceHolder )holder).choiceCount.setText();
//            Log.d("innerappder,", "here:"+Integer.toString(choices.size()));
//        }

    }

    @Override
    public int getItemCount() {
        return question.getChoices().size();
    }

    public static class InnerMultiChoiceHolder extends RecyclerView.ViewHolder {
         TextView choiceText;
         TextView choiceCount;

        public InnerMultiChoiceHolder(@NonNull View itemView) {
            super(itemView);
            choiceText = itemView.findViewById(R.id.inner_choice_text);
            choiceCount = itemView.findViewById(R.id.inner_choice_count);
        }
    }


}
