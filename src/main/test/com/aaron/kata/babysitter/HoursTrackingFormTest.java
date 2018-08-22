package com.aaron.kata.babysitter;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.junit.Assert.assertEquals;

/**
 * Note: The @DateTimeFormat annotation is part of the spring container and cannot be tested in this Dto test. It will
 * be tested in the mvc controllers test
 */
public class HoursTrackingFormTest {
    private static final String HOURS_INPUT_PATTERN = "hh:mma";
    private HoursTrackingForm tested;

    @Before
    public void setUp() throws Exception {
        tested = new HoursTrackingForm();
    }

    @Test
    public void Test_StartTimeMalformed_ErrorMessage() throws Exception {
        DateTime validTimeInput = DateTime.parse("12:30pm", DateTimeFormat.forPattern(HOURS_INPUT_PATTERN));
        tested.setStartTime(validTimeInput);
        assertEquals("Invalid time format, please enter hours and minutes. Example: 4:30pm",
                validTimeInput, tested.getStartTime()
        );
    }

    @Test
    public void Test_EndTimeMalformed_ErrorMessage() throws Exception {
        DateTime validTimeInput = DateTime.parse("12:30pm", DateTimeFormat.forPattern(HOURS_INPUT_PATTERN));
        tested.setEndTime(validTimeInput);
        assertEquals("Invalid time format, please enter hours and minutes. Example: 4:30pm",
                validTimeInput,
                tested.getEndTime()
        );
    }

    @Test
    public void Test_GetStartTimeAfterMidnight_RoundToNextDat() throws Exception {
        tested.setStartTime(DateTime.parse("12:00am", DateTimeFormat.forPattern(HOURS_INPUT_PATTERN)));
        assertEquals("Start time did not round as expected",
                new DateTime("1970-01-02T00:00:00.000-05:00"),
                tested.getStartTime()
        );
    }

    @Test
    public void Test_GetEndTimeAfterMidnight_RoundToNextDat() throws Exception {
        tested.setEndTime(DateTime.parse("12:00am", DateTimeFormat.forPattern(HOURS_INPUT_PATTERN)));
        assertEquals("End time did not round as expected",
                new DateTime("1970-01-02T00:00:00.000-05:00"),
                tested.getEndTime()
        );
    }

}