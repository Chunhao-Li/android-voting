package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TextQuestionActivity extends AppCompatActivity {
    private String text_question;
    public static final String GET_TEXT_QUESTION = "com.example.votingapp.GET_QUESTION";
    private EditText mQuestion;
    private Button saveButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_question);

        mQuestion = findViewById(R.id.text_q_editText);
        saveButton = findViewById(R.id.button_save);
        cancelButton = findViewById(R.id.button_cancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text_question = mQuestion.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(GET_TEXT_QUESTION, text_question);
                setResult(RESULT_OK, replyIntent);
                finish();

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }


}
