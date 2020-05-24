package com.example.votingapp.voting_result;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.votingapp.R;

class InnerAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InnerMultiChoiceHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.inner_multi_choice_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((InnerMultiChoiceHolder )holder).choiceText.setText("item" + position);
        ((InnerMultiChoiceHolder )holder).choiceCount.setText("ans" + position);
    }

    @Override
    public int getItemCount() {
        return 100;
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
