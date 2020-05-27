package com.example.votingapp;

import androidx.annotation.NonNull;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.votingapp.CreateVotingTest.clickChildViewWithId;

public class VotingResultTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void waitForDownloading() throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInWithEmailAndPassword("test@test.com", "12345678").addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mainActivityRule.getActivity().updateUI();
                            try {
                                countDownLatch.await(3, TimeUnit.SECONDS);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
            );
        } else {
            countDownLatch.countDown();
        }
        countDownLatch.await(5, TimeUnit.SECONDS);

    }

    @Test
    public void questionTitleExistTest() throws InterruptedException {
        onView(withId(R.id.main_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(
                        R.id.button_view_voting)));
        // wait for downloading
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);
        RecyclerViewMatcher resultRecyclerview = new RecyclerViewMatcher(R.id.voting_result_recyclerview);
        onView(resultRecyclerview.atPositionOnView(0, R.id.result_text_q_title)).check(matches(
                withText("Here is a test text question")));
    }

    @Test
    public void questionViewDetailExistTest() throws InterruptedException {
        onView(withId(R.id.main_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(
                        R.id.button_view_voting)));
        // wait for downloading
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);
        RecyclerViewMatcher resultRecyclerview = new RecyclerViewMatcher(R.id.voting_result_recyclerview);
        onView(resultRecyclerview.atPositionOnView(0, R.id.button_text_detail)).check(matches(
                withText("View detail")));
    }

    @Test
    public void answerExistTest() throws InterruptedException {
        onView(withId(R.id.main_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(
                        R.id.button_view_voting)));
        // wait for downloading
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(3, TimeUnit.SECONDS);

        onView(withId(R.id.voting_result_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(
                        R.id.button_text_detail)));
        RecyclerViewMatcher detailRecyclerview = new RecyclerViewMatcher(R.id.text_result_detail_recyclerview);
        onView(detailRecyclerview.atPositionOnView(0, R.id.text_result_detail_answer)).check(matches(
                withText("A test answer.")));
    }
}
