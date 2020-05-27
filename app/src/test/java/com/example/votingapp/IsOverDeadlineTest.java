package com.example.votingapp;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;
import static com.example.votingapp.MainActivity.*;

public class IsOverDeadlineTest {
    private String hour;
    private String minute;
    private String year;
    private String month;
    private String day;

    @Before
    public void getCurrentTime() {
        Calendar c = Calendar.getInstance();
        hour = Integer.toString(c.get(Calendar.HOUR_OF_DAY));
        minute = Integer.toString(c.get(Calendar.MINUTE));
        year = Integer.toString(c.get(Calendar.YEAR));
        month = Integer.toString(c.get(Calendar.MONTH) + 1);
        day = Integer.toString(c.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void beforeDeadlineTest() {
        String deadline = String.valueOf(Integer.parseInt(year) + 1) + "/" + month + "/" +
                day + "/" + hour + "/" + minute;
        assertFalse(isOverDeadline(deadline));
    }

    @Test
    public void afterDeadlineTest() {
        String deadline = String.valueOf(Integer.parseInt(year) - 1) + "/" + month + "/" +
                day + "/" + hour + "/" + minute;
        assertTrue(isOverDeadline(deadline));
    }

}