package com.example.votingapp.view_voting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.votingapp.R;

import java.util.ArrayList;

public class TextResultDetailActivity extends AppCompatActivity {
    /**
     * This activity will show all the answers of a text question.
     */

    private int answerCount = 0;
    private ArrayList<String> allAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_result_detail);

        // Initialize
        TextView totalCount = findViewById(R.id.text_detail_total_count);
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey(ResultAdapter.GET_TEXT_STAT)) {
                allAnswers = bundle.getStringArrayList(ResultAdapter.GET_TEXT_STAT);
                if (allAnswers != null) {
                    answerCount = allAnswers.size();
                }
            }
            // show how many answers the users have collected
            String showCount = getString(R.string.text_voter_count_title) + answerCount;
            totalCount.setText(showCount);

            RecyclerView mRecyclerView = findViewById(R.id.text_result_detail_recyclerview);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            TextAnswerDetailAdapter mAdapter = new TextAnswerDetailAdapter(this, allAnswers);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}
