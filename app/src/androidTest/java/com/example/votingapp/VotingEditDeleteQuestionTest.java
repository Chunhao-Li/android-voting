package com.example.votingapp;

import androidx.test.espresso.action.GeneralSwipeAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.example.votingapp.edit_voting.VotingEditActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;

public class VotingEditDeleteQuestionTest {
    @Rule
    public ActivityTestRule<VotingEditActivity> votingEditActivityRule =
            new ActivityTestRule<>(VotingEditActivity.class);

    @Before
    public void createQuestions() {
        // create a text question
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fab_text)).perform(click());
        onView(withId(R.id.editText_text_title)).perform(typeText(
                "Here is a test text question"), closeSoftKeyboard());
        onView(withId(R.id.button_save_text)).perform(click());

        // create a multi choice question
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

    }

    @Test
    public void deleteQuestionTest() {
        onView(ViewMatchers.withId(R.id.recyclerview_edit)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, swipeLeft()));
        RecyclerViewMatcher editRecyclerview = new RecyclerViewMatcher(R.id.recyclerview_edit);
        onView(editRecyclerview.atPositionOnView(0, R.id.multiple_choice_title)).check(matches(
                withText("Here is a test multi choice question")));

    }
}
