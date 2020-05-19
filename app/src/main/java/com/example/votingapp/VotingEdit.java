package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
        //the add button with itself rotation
        mAddButton.setOnClickListener(new View.OnClickListener() {
            boolean isRotate = false;
            @Override
            public void onClick(View v) {
                isRotate = expandFAB(v, isRotate);
            }
//            once click, rotate addButton 135 degree or reset(click twice).
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
//        the choice button listener
        mChoiceButton.setOnClickListener(new View.OnClickListener(){
            @Override
//            once click the choice button, s.t.
            public void onClick(View v){

            }
        });
//        text choice button listener, when clicked to create a new voting confirm dialog
        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popCreateDialog();
            }
//            dialog to create a new voting event with title set
            private void popCreateDialog(){
                final EditText editText = new EditText(VotingEdit.this);
                final AlertDialog.Builder newVotingEventDialog = new AlertDialog.Builder(VotingEdit.this);
                final View dialogView = LayoutInflater.from(VotingEdit.this).inflate(R.layout.create_dialog,null);
                newVotingEventDialog.setTitle("Create New Voting Event").setView(editText);
                newVotingEventDialog.setMessage("give your voting event a name!");

//                if press create, record the title of voting event, go through next page
                newVotingEventDialog.setPositiveButton("create!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                      extract the title of voting event
                        EditText edit_text = (EditText) dialogView.findViewById(R.id.edit_text);
                        String titleOfVotingEvent = edit_text.getText().toString();
                        Log.d("title test print:",titleOfVotingEvent);
                        Toast.makeText(VotingEdit.this, editText.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
//                if press cancel , quit the dialog
                newVotingEventDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                newVotingEventDialog.show();
            }
        });
    }
}
