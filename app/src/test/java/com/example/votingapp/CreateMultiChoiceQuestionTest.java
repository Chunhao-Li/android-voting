package com.example.votingapp;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

import static com.example.votingapp.edit_voting.MultiChoiceActivity.*;

public class CreateMultiChoiceQuestionTest {

    @Test
    public void nullQuestionTitleTest() {
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Test choice");
        assertFalse(isValidMultiQuestion(null, choices));
    }

    @Test
    public void nullChoicesTest() {
        String title = "test title";
        assertFalse(isValidMultiQuestion(title, null));
    }

    @Test
    public void bothNullTest() {
        assertFalse(isValidMultiQuestion(null, null));
    }

    @Test
    public void emptyTitleTest() {
        String title = "";
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Test choice");
        assertFalse(isValidMultiQuestion(title, choices));
    }

    @Test
    public void emptyChoicesTest() {
        String title = "test title";
        ArrayList<String> choices = new ArrayList<>();
        assertFalse(isValidMultiQuestion(title, choices));
    }

    @Test
    public void bothEmptyTest() {
        String title = "";
        ArrayList<String> choices = new ArrayList<>();
        assertFalse(isValidMultiQuestion(title, choices));
    }

    @Test
    public void validQuestionTest() {
        String title = "test title";
        ArrayList<String> choices = new ArrayList<>();
        choices.add("Test choice");
        assertTrue(isValidMultiQuestion(title, choices));
    }
}
