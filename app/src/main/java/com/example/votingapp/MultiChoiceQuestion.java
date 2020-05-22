package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MultiChoiceQuestion extends AppCompatActivity {
    private Button saveButton;
    private String textQuestion;
    private EditText questionFiled;
    //private String choice1;
    //private String choice2;
    private EditText choice1_edittext;
    private EditText choice2_edittext;
    private ArrayList<String> choices = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_choice_question);
        questionFiled = findViewById(R.id.editText_question);

        saveButton = findViewById(R.id.save_button_multichoice_);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textQuestion = questionFiled.getText().toString();

                Intent replyIntent = new Intent();
                replyIntent.putExtra("key", textQuestion);
                replyIntent.putStringArrayListExtra("key_choices", choices);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }
}
