package com.example.votingapp.view_voting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

import java.util.ArrayList;

/**
 * This is to show all the answers of a text question.
 */
public class TextAnswerDetailAdapter extends RecyclerView.Adapter<TextAnswerDetailAdapter.AnswerViewHolder> {

    private final ArrayList<String> allAnswers;
    private Context mContext;

    TextAnswerDetailAdapter(Context context, ArrayList<String> allAnswers) {
        this.allAnswers = allAnswers;
        mContext = context;
    }

    @NonNull
    @Override
    public AnswerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.text_result_detail_item, parent, false);
        return new AnswerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerViewHolder holder, int position) {
        String curAns = allAnswers.get(position);
        holder.ans.setText(curAns);
    }

    @Override
    public int getItemCount() {
        return allAnswers.size();
    }

    static class AnswerViewHolder extends RecyclerView.ViewHolder {
        private TextView ans;

        AnswerViewHolder(@NonNull View itemView) {
            super(itemView);
            ans = itemView.findViewById(R.id.text_result_detail_answer);
        }
    }
}
