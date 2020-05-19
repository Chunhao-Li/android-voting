package com.example.votingapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.votingapp.backend_storage.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private QuestionAdapter mQuestionAdapter;

    private static final int RC_SIGN_IN = 3425;
    private static final String SIGN_IN_TAG = "Sign_in_tag";

    private MenuItem mSignInOut;

    // firebase reference
    FirebaseDatabase mDatabase;
    DatabaseReference mDatabaseRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        // Initialise fields

        // Initialise firebase reference
        mDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();


        mDatabaseRef= mDatabase.getReference();



        writeNewUser("342", "Janny", "dfsf@gamil.com");
        mDatabaseRef.child("users").child("32").child("username").setValue("fred");
        ValueEventListener userListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("MainActi: ", user.getEmail() +user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
//        mDatabaseRef.child("users").child("342").addValueEventListener(userListener);
////        Log.d("test_erereer", mDatabaseRef.child("users").push().getKey());
//        mDatabaseRef.child("users").child("342").child("email").setValue(null);


        // Initialize fields
//        mRecyclerView = findViewById(R.id.main_recyler_view);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        mMainCardAdapter = new MainCardAdapter(this);
//        mRecyclerView.setAdapter(mMainCardAdapter);


    }

    private void writeNewUser(String userId, String name, String email) {
        User user = new User(name, email);
        mDatabaseRef.child("users").child(userId).setValue(user);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSignInOut = menu.findItem(R.id.sign_in_out_item);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = auth.getCurrentUser();
                mSignInOut.setTitle(R.string.sign_out_text);

            } else {
                Log.d(SIGN_IN_TAG, "Sign in failed...");
            }
        }
    }

    public void signOut() {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // change back to sign in
                mSignInOut.setTitle(R.string.sign_in_text);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();

        if (auth.getCurrentUser() == null) {
            if (item.getTitle().toString().equals(getString(R.string.sign_out_text))) {
                item.setTitle(R.string.sign_in_text);
            }

            // Choose authentication providers

                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.AnonymousBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build()
                );

                // Create and launch sign-in intent
                startActivityForResult(AuthUI.getInstance().
                        createSignInIntentBuilder().
                        setIsSmartLockEnabled(false).
                        setAvailableProviders(providers).
                        build(), RC_SIGN_IN);


        } else { // sign out
            signOut();
        }

        return super.onOptionsItemSelected(item);
    }

    public void switchToEdit(View view) {
//        Intent intent = new Intent(this, VotingEdit.class);
//        startActivity(intent);
    }


}
