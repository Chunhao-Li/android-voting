package com.example.votingapp;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.filterEquals;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

public class AnonymousMainTest {

    @Rule
    public IntentsTestRule<MainActivity> mainIntentRule = new IntentsTestRule<>(MainActivity.class);


    @Before
    public void initialize() throws InterruptedException {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        if (auth.getCurrentUser() != null) {
            openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
            onView(withText("Sign Out")).perform(click());
        } else {
            countDownLatch.countDown();
        }


        countDownLatch.await(3, TimeUnit.SECONDS);
    }

    @Test
    public void launchVotingEditTest() {
        onView(withId(R.id.button_create_voting)).perform(click());
        intended(filterEquals(AuthUI.getInstance().
                createSignInIntentBuilder().
                setIsSmartLockEnabled(false).
                setAvailableProviders(Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build())).
                build()));
    }
}
