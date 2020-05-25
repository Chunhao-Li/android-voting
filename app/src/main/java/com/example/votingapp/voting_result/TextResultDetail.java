package com.example.votingapp.voting_result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.votingapp.R;

import java.util.ArrayList;

public class TextResultDetail extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private  int mVoterCount;
    private ArrayList<String> allAnswers;

    private TextView totalCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_result_detail);

        totalCount = findViewById(R.id.text_result_detail_answer);;


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            if (bundle.containsKey(ResultAdapter.GET_TEXT_STAT)) {
                allAnswers = bundle.getStringArrayList(ResultAdapter.GET_TEXT_STAT);
            }
            if (bundle.containsKey(ResultAdapter.GET_TEXT_COUNT)) {
                mVoterCount = bundle.getInt(ResultAdapter.GET_TEXT_COUNT);
            }
        }


    }
}
