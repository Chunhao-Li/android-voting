package com.example.votingapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.HashSet;

import static com.example.votingapp.MainActivity.*;

public class CheckVotingIdValidTest {
    private HashSet<String> validIds;

    @Before
    public void initialize() {
        validIds = new HashSet<>();
        validIds.add("123");
    }

    @Test
    public void emptyIdTest() {
        String curId = "";
        assertEquals("Empty", checkVotingIdValid(validIds, curId));
    }

    @Test
    public void invalidIdTest() {
        String curId = "234";
        assertNotEquals("Valid", checkVotingIdValid(validIds, curId));
    }

    @Test
    public void validIdTest() {
        String curId = "123";
        assertEquals("Valid", checkVotingIdValid(validIds, curId));
    }

}
