package com.example.votingapp;

import androidx.test.rule.ActivityTestRule;

import com.example.votingapp.edit_voting.VotingEditActivity;

import org.junit.Rule;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class EmptyVotingTest {
    @Rule
    public ActivityTestRule<VotingEditActivity> editActivityRule = new ActivityTestRule<>(VotingEditActivity.class);

    @Test
    public void emptyVotingTest() throws InterruptedException {
        onView(withId(R.id.save_voting_edit)).perform(click());
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        Thread.sleep(1500);
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        Thread.sleep(500);
        onView(withText("Voting is empty")).inRoot(withDecorView(not(is(editActivityRule.getActivity()
                .getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

}
