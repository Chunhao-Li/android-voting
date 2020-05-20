package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.voting_edit.TextEditQuestion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class VotingEdit extends AppCompatActivity {
    FloatingActionButton mAddButton;
    FloatingActionButton mChoiceButton;
    FloatingActionButton mTextButton;
    private final int RC_TEXT_QUESTION = 243;
    public static final String VOTING_INFO_KEY = "com.example.votingapp.VOTING_INFO";
    private ArrayList<RecyclerViewQuestionItem> questionItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;
    private boolean isOriginStatus = true;   // are fabs in their origin status

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_voting_edit) {
            saveVoting();
        }
        return true;
    }

    private void saveVoting() {
        Intent toMainIntent = new Intent();
        toMainIntent.putParcelableArrayListExtra(VOTING_INFO_KEY, questionItems);
        setResult(RESULT_OK, toMainIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_voting_edit, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting_edit);

        // Initialize fields
        mRecyclerView = findViewById(R.id.recyclerview_edit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuestionAdapter(this, questionItems);
        mRecyclerView.setAdapter(mAdapter);


        // enable item to swipe(delete) or move(change order)
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT |
                ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT

        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                int fromPos = viewHolder.getAdapterPosition();
                int toPos = target.getAdapterPosition();
                Collections.swap(questionItems, fromPos, toPos);
                mAdapter.notifyItemMoved(fromPos, toPos);
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                questionItems.remove(pos);
                mAdapter.notifyItemRemoved(pos);
            }
        });
        helper.attachToRecyclerView(mRecyclerView);

        mAddButton = findViewById(R.id.fab_add);
        mChoiceButton = findViewById(R.id.fab_choice);
        mTextButton = findViewById(R.id.fab_text);


        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOriginStatus) {
                    updateFAB();
                } else {
                    initializeFAB();
                }
            }
        });
        mChoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop to choice question edit
                initializeFAB();
            }
        });
        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop to the text question edit page.
                initializeFAB();
                Intent intent = new Intent(VotingEdit.this, CreateTextQ.class);
                startActivityForResult(intent, RC_TEXT_QUESTION);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_TEXT_QUESTION) {
            Log.d("result_code:", Integer.toString(requestCode));
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String text_question = data.getStringExtra(CreateTextQ.GET_TEXT_QUESTION);
                    TextEditQuestion textQuestion = new TextEditQuestion(text_question);
                    int oldQuestionItemsSize = questionItems.size();
                    questionItems.add(new RecyclerViewQuestionItem(textQuestion, QuestionType.TEXT_QUESTION));
                    mAdapter.notifyItemInserted(oldQuestionItemsSize);
                }

            }

        }

    }


    public void initializeFAB() {
        mAddButton.animate().rotation(0);
        mChoiceButton.setVisibility(View.INVISIBLE);
        mTextButton.setVisibility(View.INVISIBLE);
        isOriginStatus = true;
    }

    public void updateFAB() {
        mAddButton.animate().rotation(135);
        mChoiceButton.setVisibility(View.VISIBLE);
        mTextButton.setVisibility(View.VISIBLE);
        isOriginStatus = false;
    }
}
