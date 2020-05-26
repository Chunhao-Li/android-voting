package com.example.votingapp.edit_voting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.votingapp.MainActivity;
import com.example.votingapp.R;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.data_type.question.QuestionParcel;
import com.example.votingapp.data_type.question.TextQuestionParcel;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This activity is for creating a voting.
 */
public class VotingEditActivity extends AppCompatActivity {


    private final int RC_TEXT_QUESTION = 243;
    private final int RC_MULTI_CHOICE_QUESTION = 245;
    public static final String VOTING_INFO_KEY = "com.example.votingapp.VOTING_INFO";
    public static final String DEADLINE_KEY = "com.example.votingapp.DEADLINE";
    public static final String GET_VOTING_TITLE = "com.example.votingapp.VOTING_TITLE";
    private ArrayList<QuestionParcel> questionItems = new ArrayList<>();
    private QuestionAdapter mAdapter;
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
        /*
        This method will enable creators to pick up a deadline for the voting,
        and save the created voting on the cloud.
         */
        Toast.makeText(this, "Choose a deadline",
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
        RecyclerView mRecyclerView = findViewById(R.id.recyclerview_edit);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new QuestionAdapter(this, questionItems);
        mRecyclerView.setAdapter(mAdapter);

        // Creators need to set the title before editing questions
        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(MainActivity.GET_VOTING_TITLE)) {
            votingTitle = bundle.getString(MainActivity.GET_VOTING_TITLE);
        }

        // Enable questions to swipe(delete) or move(change order)
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


        findViewById(R.id.fab_choice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionMenu) findViewById(R.id.fab_menu)).close(true);
                Intent intent = new Intent(VotingEditActivity.this, MultiChoiceActivity.class);
                startActivityForResult(intent, RC_MULTI_CHOICE_QUESTION);
            }
        });

        findViewById(R.id.fab_text).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FloatingActionMenu) findViewById(R.id.fab_menu)).close(true);
                Intent intent = new Intent(VotingEditActivity.this, TextQuestionActivity.class);
                startActivityForResult(intent, RC_TEXT_QUESTION);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_TEXT_QUESTION) {
            if (resultCode == RESULT_OK) {
                // Create a text question
                if (data != null) {
                    String text_question = data.getStringExtra(TextQuestionActivity.GET_TEXT_QUESTION);
                    int oldQuestionItemsSize = questionItems.size();
                    questionItems.add(new TextQuestionParcel(text_question));
                    mAdapter.notifyItemInserted(oldQuestionItemsSize);
                }

            }

        } else if (requestCode == RC_MULTI_CHOICE_QUESTION) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    // create a multi choice quesiton
                    String textQuestion = data.getStringExtra(MultiChoiceActivity.GET_MULTI_CHOICE_QUESTION);
                    ArrayList<String> choices = data.getStringArrayListExtra(MultiChoiceActivity.GET_MULTI_CHOICE_CHOICES);
                    int oldQuestionItemsSize = questionItems.size();
                    questionItems.add(new MultiChoiceParcel(textQuestion, choices));
                    mAdapter.notifyItemInserted(oldQuestionItemsSize);

                }
            }
        }

    }

    public void processDatePicker(int year, int month, int dayOfMonth) {
        /*
        This method will update the deadline of the selected date
         */
        String month_str = Integer.toString(month + 1);
        String day_str = Integer.toString(dayOfMonth);
        String year_str = Integer.toString(year);
        deadline = (year_str + "/" + month_str + "/" + day_str);
        pickTime();

    }

    public void processTimePickerAndSave(int hour, int minute) {
        /*
        This method will update the deadline of the selected time,
        and save the voting.
         */
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

    }
}
