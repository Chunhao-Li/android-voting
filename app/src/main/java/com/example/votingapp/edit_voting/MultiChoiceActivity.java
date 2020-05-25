package com.example.votingapp.edit_voting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.votingapp.R;

import java.util.ArrayList;

public class MultiChoiceActivity extends AppCompatActivity {
    /**
     * Activity for creating a multiple choice question.
     */
    private String textQuestion;
    private EditText questionFiled;
    public static final String GET_MULTI_CHOICE_QUESTION= "MultiChoiceQuestion.question";
    public static final String GET_MULTI_CHOICE_CHOICES= "MultiChoiceQuestion.choices";
    private ArrayList<String> choices = new ArrayList<>();
    private EditText tempChoiceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_choice_question);
        questionFiled = findViewById(R.id.editText_question);
        //Passing data to VotingEditActivity
        Button saveButton = findViewById(R.id.multi_button_save);
        Button cancelButton = findViewById(R.id.multi_button_cancel);



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

                ArrayList<String> removeEmptyChoices = new ArrayList<>();
                for (String choice: choices) {
                    if (!choice.isEmpty()) {
                        removeEmptyChoices.add(choice);
                    }
                }


                Intent replyIntent = new Intent();
                replyIntent.putExtra(GET_MULTI_CHOICE_QUESTION, textQuestion);
                replyIntent.putStringArrayListExtra(GET_MULTI_CHOICE_CHOICES, removeEmptyChoices);
                //  passing to voting edit
                if (removeEmptyChoices.size() == 0 || textQuestion.isEmpty()) {
                    Toast.makeText(MultiChoiceActivity.this, "Invalid questions!", Toast.LENGTH_SHORT).show();
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
