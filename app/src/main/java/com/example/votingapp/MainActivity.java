package com.example.votingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.votingapp.data_storage.QuestionType;
import com.example.votingapp.data_storage.firebase_data.MultipleChoiceQuestionStatistics;
import com.example.votingapp.data_storage.firebase_data.QuestionStatistics;
import com.example.votingapp.data_storage.firebase_data.TextQuestionStatistics;
import com.example.votingapp.data_storage.firebase_data.User;
import com.example.votingapp.data_storage.firebase_data.VotingResult;
import com.example.votingapp.voting_edit.EditMultipleChoiceQuestion;
import com.example.votingapp.voting_edit.EditQuestion;
import com.example.votingapp.voting_edit.EditTextQuestion;
import com.example.votingapp.voting_edit.RecyclerViewQuestionItem;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VotingAdapter mVotingAdapter;

    private EditText votingIdInput;

    private static final int RC_SIGN_IN = 3425;
    private static final int RC_VOTING_EDIT = 2983;
    private static final String TAG = "MainActivity";
    public static final String GET_VOTING_TITLE = "com.example.votingapp.GET_VOTING_TITLE";

    private MenuItem mSignIn;
    private MenuItem mSignOut;

//    private ArrayList<String> votingTitles = new ArrayList<>();
//    private ArrayList<String> createdVotingIds = new ArrayList<>();
//    private HashMap<String, String> votings = new HashMap<>();
    private ArrayList<String> votingInfo = new ArrayList<>();
    private HashSet<String> allVotingId = new HashSet<>();
    private VotingResult newVoting;

    private boolean needUpdate = true;

    // firebase reference
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseUserRef;
    DatabaseReference mDatabaseVotingRef;
    //    DatabaseReference mDatabaseVotingRef;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseUser mUser;

    private String curUserId;   // userId for current user


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        // Initialise fields
        mRecyclerView = findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVotingAdapter = new VotingAdapter(this, votingInfo);
        mRecyclerView.setAdapter(mVotingAdapter);

        votingIdInput = findViewById(R.id.editText_do_voting);
        // Initialise firebase reference
        mDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        mDatabaseUserRef = mDatabase.getReference("users");
        mDatabaseVotingRef = mDatabase.getReference("votings");

        mUser = auth.getCurrentUser();
        if (allVotingId.size() == 0) {
            obtainAllVotingId();
        }

        if (mUser != null) {
            updateUser();
            Log.d("Oncreate:", "eraer");
        }


        final Intent votingEditIntent = getIntent();
        if (votingEditIntent.getExtras() != null &&
                votingEditIntent.hasExtra(VotingEditActivity.VOTING_INFO_KEY)) {
            ArrayList<RecyclerViewQuestionItem> newVoting =
                    votingEditIntent.getParcelableArrayListExtra(VotingEditActivity.VOTING_INFO_KEY);
            String deadline = votingEditIntent.getStringExtra(VotingEditActivity.DEADLINE_KEY);
            String votingTitle = votingEditIntent.getStringExtra(VotingEditActivity.GET_VOTING_TITLE);
            if (newVoting != null && deadline != null && newVoting.size() > 0) {
                Log.d("getFromEdit","hehehe");
                saveVoting(newVoting, deadline, votingTitle);
                needUpdate = false;
            }

        }
        // Initialize fields

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                Log.d("onAuthStateChanged:", "initialise");
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {

                    // signed in user

                    updateUser();

                } else {
                    curUserId = null;
                }
                if (needUpdate) {
                    updateUI();
                } else {
                    needUpdate = true;
                }
                Log.d("onAuthStateChanged:", Integer.toString(votingInfo.size()));
            }
        };
        auth.addAuthStateListener(mAuthStateListener);

    }

    private void updateUI() {
        new UpdateUiTask().execute();
    }

    private class UpdateUiTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onProgressUpdate(Void... values) {
            Log.d("onProgressUpdate", Integer.toString(votingInfo.size()));
            mVotingAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // when signing out
            Log.d("onPostExecute", Integer.toString(votingInfo.size()));
            mVotingAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            votingInfo.clear();
            mDatabase.getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (curUserId != null) {
                        if (dataSnapshot.child("users").child(curUserId).hasChild("votings")) {
                            for (DataSnapshot childVotingId :
                                    dataSnapshot.child("users").child(curUserId).child("votings").getChildren()) {
                                String votingRId = childVotingId.getValue().toString();
                                String votingTitle = dataSnapshot.child("votings").child(votingRId)
                                        .child("votingTitle").getValue().toString();
                                String curVotingInfo = votingRId + "," + votingTitle;
                                votingInfo.add(curVotingInfo);
                                Log.d("obtainCreate Here", Integer.toString(votingInfo.size()));
                                publishProgress();
                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        @Override
        protected Void doInBackground(Void... voids) {
          return null;

        }
    }



    private void updateUser() {
        String email = mUser.getEmail();
        String name = mUser.getDisplayName();

        User newUser = new User(name, email);
        curUserId = newUser.getUserId();
        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("username", name);
        userUpdate.put("email", email);
        mDatabaseUserRef.child(curUserId).updateChildren(userUpdate);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSignIn = menu.findItem(R.id.sign_in_item);
        mSignOut = menu.findItem(R.id.sign_out_item);
        if (mUser != null) {
            mSignIn.setVisible(false);
            mSignOut.setVisible(true);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onACtivi,requestCode", Integer.toString(requestCode));
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                mSignIn.setVisible(false);
                mSignOut.setVisible(true);

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled...", Toast.LENGTH_SHORT).show();
//                Log.d(SIGN_IN_TAG, "Sign in failed...");
            }
        }
    }

    private void saveVoting(ArrayList<RecyclerViewQuestionItem> newVotingQuestions, String deadline, String votingTitle) {
        // save the new Voting to votingResultList
        ArrayList<QuestionStatistics> questionStatistics = new ArrayList<>();
        for (RecyclerViewQuestionItem item : newVotingQuestions) {
            EditQuestion question = item.getData();
            if (item.getType() == QuestionType.TEXT_QUESTION) {

                TextQuestionStatistics textQuestionStatistics =
                        new TextQuestionStatistics(((EditTextQuestion) question).getQuestionString(),
                                0, new ArrayList<String>());
                questionStatistics.add(textQuestionStatistics);
            } else if (item.getType() == QuestionType.MULTI_CHOICE) {
                MultipleChoiceQuestionStatistics multiChoiceStatistics =
                        new MultipleChoiceQuestionStatistics(((EditMultipleChoiceQuestion) question).getQuestionString(),
                                0, ((EditMultipleChoiceQuestion) question).getChoices(), new ArrayList<Integer>());
                questionStatistics.add(multiChoiceStatistics);
            }
        }

        if (deadline != null) {
            String[] splitDeadline = deadline.split("/");
            // ensures the date and time are both set
            if (splitDeadline.length == 5) {
                newVoting = new VotingResult(
                        curUserId, deadline, questionStatistics, votingTitle);
//                votingResultList.add(newVotingResult);
//                votingTitles.add(votingTitle);
//                obtainCreatedVotingId(true);
                saveVotingOnCloud();

            }
        }
    }

    private void saveVotingOnCloud() {
//        createdVotingIds = obtainCreatedVotingId();
        Log.d("saveVotingONcloud", Integer.toString(votingInfo.size()));

        DatabaseReference newVotingRef = mDatabaseVotingRef.push();
        String votingResultKey = newVotingRef.getKey(); // get unique id for the voting result
//            createdVotingIds.add(votingResultKey);
        newVoting.setVotingResultId(votingResultKey);

        Log.d("saveVotingoncloud:", curUserId);
        newVotingRef.child("votingTitle").setValue(newVoting.getVotingTitle());

        newVotingRef.child("creatorUid").setValue(newVoting.getCreatorUid());
        newVotingRef.child("deadline").setValue(newVoting.getDeadline());

        ArrayList<QuestionStatistics> questionStatistics = newVoting.getQuestionStatistics();

        // save question statistic on the server
        // TODO store multiple choice questions
        for (int i = 0; i < questionStatistics.size(); i++) {
            QuestionStatistics curQuestionStat = questionStatistics.get(i);
            if (curQuestionStat.getQuestionType() == QuestionType.TEXT_QUESTION) {
                DatabaseReference curQuestionStatRef = newVotingRef.child("questions").child(Integer.toString(i));
                curQuestionStatRef.child("question").setValue(curQuestionStat.getQuestionString());
                curQuestionStatRef.child("totalVoterCount").setValue(curQuestionStat.getTotalVoterCount());
//                ArrayList<String> answers = ((TextQuestionStatistics) curQuestionStat).getAnwsers();
//                for (String ans : answers) {
//                    curQuestionStatRef.child("answer").push().setValue(ans);
//                }
            }
        }

        // save voting result id for the user
        mDatabaseUserRef.child(curUserId).child("votings").
               push().setValue(votingResultKey);
        updateUI();


    }

    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // change back to sign in
                Log.d("signOut", "here");
                mSignIn.setVisible(true);
                mSignOut.setVisible(false);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.sign_in_item:
                popToSignIn();

            case R.id.sign_out_item:
                signOut();
        }


        return super.onOptionsItemSelected(item);
    }


    public void popToSignIn() {
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
//                new AuthUI.IdpConfig.AnonymousBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(AuthUI.getInstance().
                createSignInIntentBuilder().
                setIsSmartLockEnabled(false).
                setAvailableProviders(providers).
                build(), RC_SIGN_IN);

    }


    @Override
    protected void onStop() {
        super.onStop();
        Log.d("onStop","hey");
//        auth.removeAuthStateListener(mAuthStateListener);
    }

    public void launchVotingEdit(View view) {
        if (mUser == null) {
            // not signed in
//            Toast.makeText(this, "Signed in to continue...", Toast.LENGTH_SHORT).show();
            popToSignIn();
        } else {
            // Build a dialog for title
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title for your voting  ");
            final EditText votingTitleInput = new EditText(this);
            votingTitleInput.setInputType(InputType.TYPE_CLASS_TEXT);
            votingTitleInput.setText(R.string.default_voting_title);
            builder.setView(votingTitleInput);

            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    m_Text = input.getText().toString();
                    String title = votingTitleInput.getText().toString();
//                    Log.d("create_Title_here", title);
                    if (title.length() > 0) {
//                        Toast.makeText(MainActivity.this, "create survey", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, VotingEditActivity.class);
                        intent.putExtra(GET_VOTING_TITLE, title);
                        dialog.dismiss();
                        startActivity(intent);
//                        finish();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();

        }
    }


    public void doVoting(View view) {
//        String inputId = votingIdInput.getText().toString();
//        if (inputId.length() > 0) {
//            new DoVotingCheckingTask().execute(inputId);
//        }

        final String inputId = votingIdInput.getText().toString();
        votingIdInput.getText().clear();
        if (inputId.length() == 0) {
//            cancel(true);
            Toast.makeText(this, "Input is empty!", Toast.LENGTH_SHORT).show();
        } else if (!allVotingId.contains(inputId)) {
            Toast.makeText(this, "Invalid voting id!", Toast.LENGTH_SHORT).show();
        } else {


            mDatabaseVotingRef.child(inputId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String deadline = dataSnapshot.child("deadline").getValue().toString();
                    String[] deadlineSplit = deadline.split("/");
                    Log.d("currentDeadline, ", deadline);
                    final Calendar c = Calendar.getInstance();
                    long rightNow = c.getTimeInMillis();
                    Log.d("isClosed rightnow", Long.toString(rightNow));

                    c.set(Calendar.YEAR, Integer.parseInt(deadlineSplit[0]));
                    c.set(Calendar.MONTH, Integer.parseInt(deadlineSplit[1]) - 1);
                    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(deadlineSplit[2]));
                    c.set(Calendar.HOUR, Integer.parseInt(deadlineSplit[3]));
                    c.set(Calendar.MINUTE, Integer.parseInt(deadlineSplit[4]));
                    long deadlineTime = c.getTimeInMillis();

                    boolean isClosed = (rightNow > deadlineTime);
                    if (isClosed) {
                        Toast.makeText(MainActivity.this, "The voting is closed!", Toast.LENGTH_SHORT).show();
                    }

                    mDatabaseVotingRef.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    private void obtainAllVotingId() {
        mDatabaseVotingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot childVotingId : dataSnapshot.getChildren()) {
                    String votingId = childVotingId.getKey().toString();
                    allVotingId.add(votingId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }


    private class DoVotingCheckingTask extends AsyncTask<String, Void, Void> {
        boolean isClosed;
        final long[] timeList = new long[2];
        String inputId;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(String... strings) {
            mDatabaseVotingRef.child(strings[0]).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String deadline = dataSnapshot.child("deadline").getValue().toString();
                    String[] deadlineSplit = deadline.split("/");
                    Log.d("currentDeadline, ", deadline);
                    final Calendar c = Calendar.getInstance();
                    long rightNow = c.getTimeInMillis();
                    Log.d("isClosed rightnow", Long.toString(rightNow));
                    timeList[0] = rightNow;

//                    Log.d("isClosed Rightnow", Integer.toString(year)+"/" + Integer.toString(month)+"/"+
//                            Integer.toString(day)+"/" + Integer.toString(hour)+"/" + Integer.toString(hour)+"/" +
//                            Integer.toString(minute));

                    c.set(Calendar.YEAR, Integer.parseInt(deadlineSplit[0]));
                    c.set(Calendar.MONTH, Integer.parseInt(deadlineSplit[1]) - 1);
                    c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(deadlineSplit[2]));
                    c.set(Calendar.HOUR, Integer.parseInt(deadlineSplit[3]));
                    c.set(Calendar.MINUTE, Integer.parseInt(deadlineSplit[4]));
                    long deadlineTime = c.getTimeInMillis();
                    timeList[1] = deadlineTime;
                    Log.d("isClosed deadline", Long.toString(deadlineTime));

                    isClosed = (rightNow > deadlineTime);
                    Log.d("Compare", Boolean.toString(isClosed));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
//            isClosed = timeList[0]>timeList[1];
            Log.d("isClosed", Boolean.toString(isClosed));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            votingIdInput.getText().clear();
            if (isClosed) {
                Toast.makeText(MainActivity.this, "The voting is closed!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
