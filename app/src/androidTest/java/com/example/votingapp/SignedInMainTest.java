package com.example.votingapp;

import android.util.Log;

import static androidx.test.espresso.Espresso.onView;

import androidx.annotation.NonNull;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static androidx.test.espresso.assertion.ViewAssertions.matches;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class SignedInMainTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    FirebaseAuth auth;
    private CountDownLatch authSignal = new CountDownLatch(1);

    @Before
    public void initializeFields() throws InterruptedException {
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInWithEmailAndPassword("test@test.com", "12345678").addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            authSignal.countDown();
                        }
                    }
            );
        } else {
            authSignal.countDown();
        }
        authSignal.await(5, TimeUnit.SECONDS);
    }


    @Test
    public void launchVotingEditTest() {
        onView(withId(R.id.button_create_voting)).perform(click());
        onView(withText("Title for your voting:")).check(matches(isDisplayed()));

    }

    @Test
    public void emptyVotingIdTest() {
        onView(withId(R.id.button_do_voting)).perform(click());
        onView(withText("Input is empty!")).inRoot(
                withDecorView(not(is(mainActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));
    }
}
