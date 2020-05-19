package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VotingEdit extends AppCompatActivity {
    FloatingActionButton mAddButton;
    FloatingActionButton mChoiceButton;
    FloatingActionButton mTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_edit);

        // Initialize fields
        mAddButton = findViewById(R.id.fab_add);
        mChoiceButton = findViewById(R.id.fab_choice);
        mTextButton = findViewById(R.id.fab_text);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            boolean isRotate = false;
            @Override
            public void onClick(View v) {
                isRotate = expandFAB(v, isRotate);
            }

            private boolean expandFAB(View v, boolean isRotate) {
                if (!isRotate) {
                    v.animate().rotation(135);
                    mChoiceButton.setVisibility(View.VISIBLE);
                    mTextButton.setVisibility(View.VISIBLE);
                } else {
                    v.animate().rotation(0);
                    mChoiceButton.setVisibility(View.INVISIBLE);
                    mTextButton.setVisibility(View.INVISIBLE);
                }
                return !isRotate;
            }
        });


    }
}
