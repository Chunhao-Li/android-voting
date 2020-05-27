package com.example.votingapp;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runners.MethodSorters;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreateVotingTest {
    @Rule
    public ActivityTestRule<MainActivity> mainActivityRule = new ActivityTestRule<>(MainActivity.class);

    FirebaseAuth auth;
    private CountDownLatch authSignal = new CountDownLatch(1);
    @Before
    public void initialize() throws InterruptedException {
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
    public void emptyQuestionTest() throws InterruptedException {
        // create a voting with default name
        onView(withId(R.id.button_create_voting)).perform(click());
        onView(withText("CREATE")).check(matches(isDisplayed())).perform(click());
        onView(withId(R.id.save_voting_edit)).perform(click());
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        Thread.sleep(1000);
        onView(withText("Voting is empty")).inRoot(withDecorView(not(is(mainActivityRule.
                getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void validVotingTest() throws InterruptedException {

        // create a voting with default name
        onView(withId(R.id.button_create_voting)).perform(click());
        onView(withText("CREATE")).check(matches(isDisplayed())).perform(click());
        // create a text question
        onView(allOf(withParent(withId(R.id.fab_menu)), withClassName(endsWith("ImageView")), isDisplayed()))
                .perform(click());
        onView(withId(R.id.fab_text)).perform(click());
        onView(withId(R.id.editText_text_title)).perform(typeText(
                "Here is a test text question"), closeSoftKeyboard());
        onView(withId(R.id.button_save_text)).perform(click());
        onView(withId(R.id.save_voting_edit)).perform(click());
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        onView(withText("OK")).check(matches(isDisplayed())).perform(click());
        RecyclerViewMatcher editRecyclerview = new RecyclerViewMatcher(R.id.main_recyclerview);
        // check there is a new voting at position 1
        onView(editRecyclerview.atPositionOnView(1, R.id.voting_title)).check(matches(
                withText("New Voting")));
        authSignal = new CountDownLatch(2);
        authSignal.await(1, TimeUnit.SECONDS);
        // delete voting after creating
        onView(withId(R.id.main_recyclerview)).perform(
                RecyclerViewActions.actionOnItemAtPosition(1, clickChildViewWithId(
                        R.id.button_delete_voting)));

    }


    /**
     * Source: https://stackoverflow.com/a/30338665/10400661
     * @param id: id of the Button inside a Recyclerview item
     * @return a ViewAction
     */
    public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };


    }
}
