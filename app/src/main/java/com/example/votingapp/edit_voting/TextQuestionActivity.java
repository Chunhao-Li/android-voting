package com.example.votingapp.edit_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingapp.R;

public class TextQuestionActivity extends AppCompatActivity {
    /**
     * Activity for creating a text question.
     */
    private String textQuestion;
    public static final String GET_TEXT_QUESTION = "com.example.votingapp.GET_QUESTION";
    private EditText mQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_question);

        mQuestion = findViewById(R.id.text_q_editText);
        Button saveButton = findViewById(R.id.button_save);
        Button cancelButton = findViewById(R.id.button_cancel);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textQuestion = mQuestion.getText().toString();
                Intent replyIntent = new Intent();
                replyIntent.putExtra(GET_TEXT_QUESTION, textQuestion);
                if (textQuestion.isEmpty()) {
                    Toast.makeText(TextQuestionActivity.this,
                            "Invalid question!", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_CANCELED);
                } else {
                    setResult(RESULT_OK, replyIntent);
                }
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
