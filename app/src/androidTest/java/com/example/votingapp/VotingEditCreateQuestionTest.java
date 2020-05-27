package com.example.votingapp;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.votingapp.edit_voting.VotingEditActivity;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static org.hamcrest.Matchers.endsWith;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class VotingEditCreateQuestionTest {
    @Rule
    public ActivityTestRule<VotingEditActivity> votingEditActivityRule =
            new ActivityTestRule<>(VotingEditActivity.class);
//


    @Test
    public void createTextQuestionTest() throws InterruptedException {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fab_text)).perform(click());
        onView(withId(R.id.editText_text_title)).perform(typeText(
                "Here is a test text question"), closeSoftKeyboard());
        onView(withId(R.id.button_save_text)).perform(click());
        Thread.sleep(1000);
        RecyclerViewMatcher editRecyclerview = new RecyclerViewMatcher(R.id.recyclerview_edit);
        onView(editRecyclerview.atPositionOnView(0, R.id.text_q_question)).check(matches(
                withText("Here is a test text question")));


    }

    @Test
    public void createMultiChoiceQuestionText() throws InterruptedException {
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fab_choice)).perform(click());
        onView(withId(R.id.editText_multi_choice_title)).perform(typeText(
                "Here is a test multi choice question"), closeSoftKeyboard());
        onView(withId(R.id.editText_choice0)).perform(typeText(
                "Test choice 1"), closeSoftKeyboard());
        onView(withId(R.id.editText_choice1)).perform(typeText(
                "Test choice 2"), closeSoftKeyboard());
        onView(withId(R.id.button_multi_save)).perform(click());
        Thread.sleep(1000);
        RecyclerViewMatcher editRecyclerview = new RecyclerViewMatcher(R.id.recyclerview_edit);
        onView(editRecyclerview.atPositionOnView(0, R.id.multiple_choice_title)).check(matches(
                withText("Here is a test multi choice question")));
        onView(editRecyclerview.atPositionOnView(0, R.id.checkBox0)).check(matches(
                withText("Test choice 1")));
        onView(editRecyclerview.atPositionOnView(0, R.id.checkBox1)).check(matches(
                withText("Test choice 2")));


    }

}
