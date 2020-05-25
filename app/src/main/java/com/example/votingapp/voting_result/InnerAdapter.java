package com.example.votingapp.voting_result;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.voting_edit.MultiChoiceQuestionParcel;

import java.util.ArrayList;

class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.InnerMultiChoiceHolder> {
//    private final ArrayList<RecyclerViewQuestionItem> questionItems;
    private final MultiChoiceQuestionParcel question;
    private final ArrayList<Integer> choiceCount;
    private int total;

    public InnerAdapter(MultiChoiceQuestionParcel multiChoiceQuestion, ArrayList<Integer> choiceCount) {
        this.question = multiChoiceQuestion;
        this.choiceCount = choiceCount;
        for (Integer count: choiceCount) {
            total += count;
        }
    }

    @NonNull
    @Override
    public InnerMultiChoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerMultiChoiceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_multi_choice_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull InnerMultiChoiceHolder holder, int position) {
//        ((InnerMultiChoiceHolder )holder).choiceText.setText("item" + position);
//        ((InnerMultiChoiceHolder )holder).choiceCount.setText("ans" + position);
        ArrayList<String> choices = question.getChoices();
        holder.choiceText.setText(choices.get(position));
        String countText= Integer.toString(choiceCount.get(position));
        holder.choiceCount.setText(countText);
        double percentage = 0;
        if (total != 0) {
             percentage = round(choiceCount.get(position) / (double) total * 100.0, 1);
        }
//        String percentageText;
//        if (percentage < 10) {
//            percentageText = " " + percentage + "%";
//        } else {
//            percentageText = percentage + "%";
//        }
        String percentageText   =percentage+"%";
        Log.d("InnerAdapter", percentageText);
        holder.choicePercent.setText(percentageText);


//        for (int i = 0; i < choices.size(); i++) {
//            ((InnerMultiChoiceHolder )holder).choiceText.setText(choices.get(i));
//            ((InnerMultiChoiceHolder )holder).choiceCount.setText();
//            Log.d("innerappder,", "here:"+Integer.toString(choices.size()));
//        }

    }

    /*/
        Round the percentage to a certain precision
     */
    private static double round (double value, int precision) { // TODO reference: https://stackoverflow.com/a/22186845/10400661
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public int getItemCount() {
        return question.getChoices().size();
    }

    public static class InnerMultiChoiceHolder extends RecyclerView.ViewHolder {
         TextView choiceText;
         TextView choiceCount;
         TextView choicePercent;

        public InnerMultiChoiceHolder(@NonNull View itemView) {
            super(itemView);
            choiceText = itemView.findViewById(R.id.inner_choice_text);
            choiceCount = itemView.findViewById(R.id.inner_choice_count);
            choicePercent = itemView.findViewById(R.id.inner_choice_percent);
        }
    }


}
