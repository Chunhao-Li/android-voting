package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.voting_edit.DatePickerFragment;
import com.example.votingapp.voting_edit.EditTextQuestion;
import com.example.votingapp.voting_edit.QuestionAdapter;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;
import com.example.votingapp.voting_edit.TimePickerFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;

public class VotingEditActivity extends AppCompatActivity {
    FloatingActionButton mAddButton;
    FloatingActionButton mChoiceButton;
    FloatingActionButton mTextButton;
    private final int RC_TEXT_QUESTION = 243;
    private final int RC_MULTICHOICE_QUESTION = 245;
    public static final String VOTING_INFO_KEY = "com.example.votingapp.VOTING_INFO";
    public static final String DEADLINE_KEY = "com.example.votingapp.DEADLINE";
    public static final String GET_VOTING_TITLE = "com.example.votingapp.VOTING_TITLE";
    private ArrayList<RecyclerViewQuestionItem> questionItems = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private QuestionAdapter mAdapter;
    private boolean isOriginStatus = true;   // are fabs in their origin status
    private String deadline;
    private String votingTitle;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_voting_edit) {
            saveVoting();
        }
        return true;
    }

    private void saveVoting() {
        Toast.makeText(this,  "Choose a deadline",
                Toast.LENGTH_SHORT).show();
        pickDeadline();

    }

    private void pickDeadline() {
       pickDate();
    }

    private void pickDate() {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void pickTime() {
        DialogFragment dialogFragment = new TimePickerFragment();
        dialogFragment.show(getSupportFragmentManager(), "timePicker");

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
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(MainActivity.GET_VOTING_TITLE)) {
            votingTitle = bundle.getString(MainActivity.GET_VOTING_TITLE);
        }


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
                Intent intent = new Intent(VotingEditActivity.this, MultiChoiceQuestion.class);
                startActivityForResult(intent, RC_MULTICHOICE_QUESTION);
            }
        });
        mTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop to the text question edit page.
                initializeFAB();
                Intent intent = new Intent(VotingEditActivity.this, TextQuestionActivity.class);
                startActivityForResult(intent, RC_TEXT_QUESTION);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_TEXT_QUESTION) {
//            Log.d("result_code:", Integer.toString(requestCode));
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    String text_question = data.getStringExtra(TextQuestionActivity.GET_TEXT_QUESTION);
                    EditTextQuestion textQuestion = new EditTextQuestion(text_question);
                    int oldQuestionItemsSize = questionItems.size();
                    questionItems.add(new RecyclerViewQuestionItem(textQuestion, QuestionType.TEXT_QUESTION));
                    mAdapter.notifyItemInserted(oldQuestionItemsSize);
                }

            }

        } else if (requestCode == RC_MULTICHOICE_QUESTION) {
            if (resultCode == RESULT_OK) {
                if (data!=null) {
                    String textQuestion = data.getStringExtra("key");
                    ArrayList<String> choices = data.getStringArrayListExtra("key_choices");
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

    public void processDatePicker(int year, int month, int dayOfMonth) {
        String month_str = Integer.toString(month+1);
        String day_str = Integer.toString(dayOfMonth);
        String year_str  = Integer.toString(year);
        deadline = (year_str+"/" + month_str + "/" + day_str);
        pickTime();
//        Toast.makeText(this,  dateMessage,
//                Toast.LENGTH_SHORT).show();
    }

    public void processTimePicker(int hour, int minute) {
        String hour_str = Integer.toString(hour);
        String minute_str = Integer.toString(minute);
        deadline = deadline + "/" + hour_str + "/" + minute_str;
        if (questionItems.size() < 1) {
            Toast.makeText(this, "Voting is empty", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Created a new voting!", Toast.LENGTH_SHORT).show();
        }
        // Back to MainActivity
        Intent toMainIntent = new Intent(this, MainActivity.class);
        toMainIntent.putParcelableArrayListExtra(VOTING_INFO_KEY, questionItems);
        toMainIntent.putExtra(DEADLINE_KEY, deadline);
        toMainIntent.putExtra(GET_VOTING_TITLE, votingTitle);
        startActivity(toMainIntent);
//        setResult(RESULT_OK, toMainIntent);
//        finish();

    }
}
