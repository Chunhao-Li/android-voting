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
    private Button cancelButton;
    private String textQuestion;
    private EditText questionFiled;
    private final int choiceSize=8;
    private final String choiceIDFormat = "editText_choice";
    private ArrayList<String> choices = new ArrayList<>();
    private EditText tempChoiceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_question);
        questionFiled = findViewById(R.id.editText_question);
        saveButton = findViewById(R.id.multi_button_save);
        cancelButton = findViewById(R.id.multi_button_cancel);



        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textQuestion = questionFiled.getText().toString();


                tempChoiceID=findViewById(R.id.editText_choice0);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice1);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice2);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice3);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice4);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice5);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice6);
                choices.add(tempChoiceID.getText().toString());
                tempChoiceID=findViewById(R.id.editText_choice7);
                choices.add(tempChoiceID.getText().toString());


                Intent replyIntent = new Intent();
                replyIntent.putExtra("key", textQuestion);
                replyIntent.putStringArrayListExtra("key_choices", choices);

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
