package com.example.votingapp.view_voting;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;
import com.example.votingapp.data_type.question.MultiChoiceParcel;

import java.util.ArrayList;

/**
 * This adapter is for showing the stat of a multi choice question in the VotingResultActivity
 */
class InnerAdapter extends RecyclerView.Adapter<InnerAdapter.InnerMultiChoiceHolder> {

    private final MultiChoiceParcel question;
    private final ArrayList<Integer> choiceCount;
    private int total;  // record all the choices of the current question

    InnerAdapter(MultiChoiceParcel multiChoiceQuestion, ArrayList<Integer> choiceCount) {
        this.question = multiChoiceQuestion;
        this.choiceCount = choiceCount;
        for (Integer count : choiceCount) {
            total += count;
        }
    }

    @NonNull
    @Override
    public InnerMultiChoiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerMultiChoiceHolder(LayoutInflater.from(
                parent.getContext()).inflate(R.layout.inner_multi_choice_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull InnerMultiChoiceHolder holder, int position) {
        ArrayList<String> choices = question.getChoices();
        holder.choiceText.setText(choices.get(position));
        String countText = Integer.toString(choiceCount.get(position));
        holder.choiceCount.setText(countText);

        // calculate the percentage of a choice
        double percentage = 0;
        if (total != 0) {
            percentage = round(choiceCount.get(position) / (double) total * 100.0, 1);
        }

        String percentageText = percentage + "%";
        holder.choicePercent.setText(percentageText);

    }

    /*/
        Round the percentage to a certain precision
     */
    private static double round(double value, int precision) { // TODO reference: https://stackoverflow.com/a/22186845/10400661
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @Override
    public int getItemCount() {
        return question.getChoices().size();
    }

    static class InnerMultiChoiceHolder extends RecyclerView.ViewHolder {
        TextView choiceText;
        TextView choiceCount;
        TextView choicePercent;

        InnerMultiChoiceHolder(@NonNull View itemView) {
            super(itemView);
            choiceText = itemView.findViewById(R.id.inner_choice_text);
            choiceCount = itemView.findViewById(R.id.inner_choice_count);
            choicePercent = itemView.findViewById(R.id.inner_choice_percent);
        }
    }


}
