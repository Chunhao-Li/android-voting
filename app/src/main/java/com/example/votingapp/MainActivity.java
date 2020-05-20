package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.votingapp.data_storage.User;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private VotingAdapter mVotingAdapter;

    private static final int RC_SIGN_IN = 3425;
    private static final int RC_VOTING_EDIT = 2983;
    private static final String TAG = "MainActivity";

    private MenuItem mSignIn;
    private MenuItem mSignOut;

    private ArrayList<RecyclerViewQuestionItem> newVoting = new ArrayList<>();
    private ArrayList<String> votings = new ArrayList<>();

    // firebase reference
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseUserRef;
    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    FirebaseUser mUser;
    String mUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        // Initialise fields
        mRecyclerView = findViewById(R.id.main_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mVotingAdapter = new VotingAdapter(this, votings);
        mRecyclerView.setAdapter(mVotingAdapter);

        // Initialise firebase reference
        mDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        mDatabaseUserRef = mDatabase.getReference("users");


        // Initialize fields

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // signed in user
                    signInInitialize();
                    mUser = user;
//                    mSignInOut.setTitle(R.string.sign_out_text);

//                    firebaseAuth.fetchSignInMethodsForEmail()
                    String email = mUser.getEmail();
                    String name = mUser.getDisplayName();
                    Map<String, Object> userUpdate = new HashMap<>();
                    userUpdate.put("username", name);
                    userUpdate.put("email", email);
                    User newUser = new User(name, email);
                    mDatabaseUserRef.child(newUser.getUserId()).updateChildren(userUpdate);
//                    if (mDatabaseUserRef.child())
//                    mDatabaseUserRef.child(email).setValue();


//                    mDatabaseUserRef.child(uid).setValue(new User(name,email));
//                String uid = mUser.getIdToken(true);

                    Log.d("TAG", "Name:" + name);
                    Log.d("TAG", "email:" + email);
                    Log.d("TAG", "uid:" + user.getUid());

                }
            }
        };


    }

    private void signInInitialize() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        auth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        auth.addAuthStateListener(mAuthStateListener);
    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabaseUserRef.child("users").child(userId).setValue(user);
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
        } else if (requestCode == RC_VOTING_EDIT) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    newVoting = data.getParcelableExtra(VotingEdit.VOTING_INFO_KEY);
                    saveVoting();
                }
            }
        }
    }

    private void saveVoting() {
        // save to the database and update the main UI
        int pos = votings.size();
        Log.d("saveVoting: votlength ", Integer.toString(votings.size()));
        votings.add("newVoting");
        mVotingAdapter.notifyItemInserted(pos);
        Log.d("saveVoting:", votings.get(0));
    }

    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // change back to sign in
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

    public void launchVotingEdit(View view) {
        if (auth.getCurrentUser() == null) {
            // not signed in
//            Toast.makeText(this, "Signed in to continue...", Toast.LENGTH_SHORT).show();
            popToSignIn();
        } else {
            Intent intent = new Intent(this, VotingEdit.class);
            startActivityForResult(intent, RC_VOTING_EDIT);
        }
    }


    public void doVoting(View view) {
    }
}
