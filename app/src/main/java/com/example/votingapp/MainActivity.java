package com.example.votingapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.votingapp.data_type.question.QuestionType;
import com.example.votingapp.data_type.user.User;
import com.example.votingapp.data_type.voting.Voting;
import com.example.votingapp.data_type.question.MultiChoiceParcel;
import com.example.votingapp.data_type.question.QuestionParcel;
import com.example.votingapp.data_type.question.TextQuestionParcel;
import com.example.votingapp.do_voting.DoVotingActivity;
import com.example.votingapp.edit_voting.VotingEditActivity;
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
    /**
     * The main class of this app.
     */

    private VotingAdapter mVotingAdapter;
    private EditText votingIdInput; // this is for doing voting
    private String votingIdInputText; // for doing voting

    private static final int RC_SIGN_IN = 3425;
    public static final String GET_VOTING_TITLE = "com.example.votingapp.GET_VOTING_TITLE";
    public static final String GET_VOTING_ID = "com.example.votingapp.GET_VOTING_ID";

    private MenuItem mSignIn;
    private MenuItem mSignOut;

    private ArrayList<ArrayList<String>> votingInfo = new ArrayList<>();    // store users' voting
    private HashSet<String> allVotingId = new HashSet<>();
    private Voting newVoting;

    private boolean needUpdateUi = true;

    // firebase reference
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseUserRef;
    DatabaseReference mDatabaseVotingRef;
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
        RecyclerView mRecyclerView = findViewById(R.id.main_recyclerview);
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

        // collect all voting ids for doing voting
        if (allVotingId.size() == 0) {
            obtainAllVotingId();
        }
        if (mUser != null) {
            addUpdateUserInfo();
        }

        // If the user has created a new voting
        final Intent votingEditIntent = getIntent();
        if (votingEditIntent.getExtras() != null &&
                votingEditIntent.hasExtra(VotingEditActivity.VOTING_INFO_KEY)) {
            ArrayList<QuestionParcel> newVotingQuestions =
                    votingEditIntent.getParcelableArrayListExtra(VotingEditActivity.VOTING_INFO_KEY);
            String deadline = votingEditIntent.getStringExtra(VotingEditActivity.DEADLINE_KEY);
            String votingTitle = votingEditIntent.getStringExtra(VotingEditActivity.GET_VOTING_TITLE);
            if (newVotingQuestions != null && deadline != null && newVotingQuestions.size() > 0) {
                saveVoting(newVotingQuestions, deadline, votingTitle);
                needUpdateUi = false;   // do not need to update the UI of the main
            }

        }

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                mUser = firebaseAuth.getCurrentUser();
                if (mUser != null) {
                    // already signed in
                    addUpdateUserInfo();
                } else {
                    curUserId = null;
                }
                if (needUpdateUi) {
                    updateUI();
                } else {
                    // when user's status change, need to update the Ui
                    needUpdateUi = true;
                }
            }
        };
        auth.addAuthStateListener(mAuthStateListener);
    }

    public void obtainAllVotingId() {
        /*
        This method will retrieve all the voting ids for doing voting.
         */
        mDatabaseVotingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childVotingId : dataSnapshot.getChildren()) {
                    String votingId = childVotingId.getKey();
                    allVotingId.add(votingId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateUI() {
        /*
        Update the Ui of MainActivity
         */
        new UpdateUiTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateUiTask extends AsyncTask<Void, Void, Void> {
        /**
         * This class will download the voting ids and titles created by
         * the current user, and update the Ui synchronously
         */
        @Override
        protected void onProgressUpdate(Void... values) {
            mVotingAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
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
                                String votingRId = childVotingId.getValue(String.class);
                                String votingTitle = null;
                                if (votingRId != null) {
                                    votingTitle = dataSnapshot.child("votings").child(votingRId)
                                            .child("votingTitle").getValue(String.class);
                                }
                                ArrayList<String> curVotingInfo = new ArrayList<>();
                                curVotingInfo.add(votingRId);
                                curVotingInfo.add(votingTitle);
                                votingInfo.add(curVotingInfo);
                                allVotingId.add(votingRId);
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


    private void addUpdateUserInfo() {
        /*
        This method will update the curUserId, and add the
        user to the server if not exists.
         */
        String email = mUser.getEmail();
        String name = mUser.getDisplayName();
        assert email != null;
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
        if (mUser != null) {    // only show signOut button when signed in
            mSignIn.setVisible(false);
            mSignOut.setVisible(true);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // Successfully signed in
                Toast.makeText(this, "Signed in!", Toast.LENGTH_SHORT).show();
                mSignIn.setVisible(false);
                mSignOut.setVisible(true);
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Sign in cancelled...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveVoting(ArrayList<QuestionParcel> newVotingQuestions, String deadline, String votingTitle) {
        /*
        This method will save the newly created voting
         */
        ArrayList<QuestionParcel> questions = new ArrayList<>();
        for (QuestionParcel question : newVotingQuestions) {
            if (question.getQuestionType() == QuestionType.TEXT_QUESTION) {
                questions.add(new TextQuestionParcel(question.getQuestionTitle()));
            } else if (question.getQuestionType() == QuestionType.MULTI_CHOICE) {
                questions.add(new MultiChoiceParcel(question.getQuestionTitle(),
                        ((MultiChoiceParcel) question).getChoices()));
            }
        }

        if (deadline != null) { // deadline must be set, otherwise the voting is invalid
            String[] splitDeadline = deadline.split("/");
            if (splitDeadline.length == 5) {    // date and time must be set
                newVoting = new Voting(curUserId, deadline, questions, votingTitle);
                saveVotingOnCloud();
            }
        }
    }

    private void saveVotingOnCloud() {
        /*
        This method will save the newly created voting on the cloud. It will only be
        called inside the saveVoting.
         */
        DatabaseReference newVotingRef = mDatabaseVotingRef.push();
        String votingId = newVotingRef.getKey(); // get unique id for the voting id

        // save voting information
        newVotingRef.child("votingTitle").setValue(newVoting.getVotingTitle());
        newVotingRef.child("creatorUid").setValue(newVoting.getCreatorUid());
        newVotingRef.child("deadline").setValue(newVoting.getDeadline());

        ArrayList<QuestionParcel> questions = newVoting.getQuestions();
        // save questions on the server
        for (int i = 0; i < questions.size(); i++) {
            QuestionParcel curQuestion = questions.get(i);
            if (curQuestion.getQuestionType() == QuestionType.TEXT_QUESTION) {
                DatabaseReference curQuestionStatRef = newVotingRef.child("questions").child(Integer.toString(i));
                curQuestionStatRef.child("question").setValue(curQuestion.getQuestionTitle());
                curQuestionStatRef.child("questionType").setValue(QuestionType.TEXT_QUESTION);
            } else if (curQuestion.getQuestionType() == QuestionType.MULTI_CHOICE) {
                DatabaseReference curQuestionStatRef = newVotingRef.child("questions").child(Integer.toString(i));
                curQuestionStatRef.child("question").setValue(curQuestion.getQuestionTitle());
                curQuestionStatRef.child("questionType").setValue(QuestionType.MULTI_CHOICE);
                ArrayList<String> choices = ((MultiChoiceParcel) curQuestion).getChoices();
                for (String choice : choices) {
                    curQuestionStatRef.child("choices").push().setValue(choice);
                }

            }

        }
        // save voting id for the creator
        mDatabaseUserRef.child(curUserId).child("votings").push().setValue(votingId);
        updateUI(); // update the ui to show the user's created voting

    }

    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // Only show signIn button if signed out
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
        /*
        It uses the firebase UI for signing in
         */
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        // Create and launch sign-in intent
        startActivityForResult(AuthUI.getInstance().
                createSignInIntentBuilder().
                setIsSmartLockEnabled(false).
                setAvailableProviders(providers).
                build(), RC_SIGN_IN);
    }


    public void launchVotingEdit(View view) {
        /*
        This method will launch the VotingEditActivity for creating a new voting
         */
        if (mUser == null) {
            // anonymous users are not allowed to create a voting
            popToSignIn();
        } else {
            // Build a dialog for input title of the voting
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Title for your voting  ");
            final EditText votingTitleInput = new EditText(this);
            votingTitleInput.setInputType(InputType.TYPE_CLASS_TEXT);
            votingTitleInput.setText(R.string.default_voting_title);
            builder.setView(votingTitleInput);
            builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String title = votingTitleInput.getText().toString();
                    if (title.length() > 0) {   // voting title should not be empty
                        Intent intent = new Intent(MainActivity.this, VotingEditActivity.class);
                        intent.putExtra(GET_VOTING_TITLE, title);
                        dialog.dismiss();
                        startActivity(intent);
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


    public void checkDoVoting(View view) {
        /*
        This method will check the voting id and the deadline for input voting id.
         */
        final String inputId = votingIdInput.getText().toString();
        votingIdInputText = inputId;
        votingIdInput.getText().clear();
        if (inputId.length() == 0) {
            Toast.makeText(this, "Input is empty!", Toast.LENGTH_SHORT).show();
        } else if (!allVotingId.contains(inputId)) {
            Toast.makeText(this, "Invalid voting id!", Toast.LENGTH_SHORT).show();
        } else {    // voting Id should be stored in the server
            mDatabaseVotingRef.child(inputId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild("deadline")) {
                        String deadline = dataSnapshot.child("deadline").getValue(String.class);
                        assert deadline != null;
                        String[] deadlineSplit = deadline.split("/");
                        final Calendar c = Calendar.getInstance();
                        long rightNow = c.getTimeInMillis();
                        c.set(Calendar.YEAR, Integer.parseInt(deadlineSplit[0]));
                        c.set(Calendar.MONTH, Integer.parseInt(deadlineSplit[1]) - 1);
                        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(deadlineSplit[2]));
                        c.set(Calendar.HOUR, Integer.parseInt(deadlineSplit[3]));
                        c.set(Calendar.MINUTE, Integer.parseInt(deadlineSplit[4]));
                        long deadlineTime = c.getTimeInMillis();

                        // Check the deadline with the local time
                        boolean isClosed = (rightNow > deadlineTime);
                        if (isClosed) {
                            Toast.makeText(MainActivity.this, "The voting is closed!", Toast.LENGTH_SHORT).show();
                        } else {
                            launchDoVoting();
                        }
                        mDatabaseVotingRef.removeEventListener(this);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid voting id!", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }

    private void launchDoVoting() {
        /*
        This method will launch DoVotingActivity
         */
        Intent intent = new Intent(this, DoVotingActivity.class);
        intent.putExtra(GET_VOTING_ID, votingIdInputText);
        startActivity(intent);
    }


}
