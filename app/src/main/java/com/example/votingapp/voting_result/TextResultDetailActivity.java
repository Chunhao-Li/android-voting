package com.example.votingapp.voting_result;

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

    private RecyclerView mRecyclerView;
    private TextAnswerDetailAdapter mAdapter;
    private  int mVoterCount;
    private ArrayList<String> allAnswers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_result_detail);

        TextView totalCount = findViewById(R.id.text_detail_total_count);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey(ResultAdapter.GET_TEXT_STAT)) {
                allAnswers = bundle.getStringArrayList(ResultAdapter.GET_TEXT_STAT);
                mVoterCount = allAnswers.size();
            }
//            if (bundle.containsKey(ResultAdapter.GET_TEXT_COUNT)) {
//                mVoterCount = bundle.getInt(ResultAdapter.GET_TEXT_COUNT);
//            }
        }
        Log.d("TextResult", allAnswers.get(0));
        String showCount = getString(R.string.text_voter_count_title) + mVoterCount;
        totalCount.setText(showCount);

        mRecyclerView = findViewById(R.id.text_result_detail_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TextAnswerDetailAdapter(this, allAnswers);
        mRecyclerView.setAdapter(mAdapter);




    }
}
