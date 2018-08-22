package com.aaron.kata.babysitter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HoursCalculationServiceImplTest {

    private static final String HOURS_FORMAT = "hh:mma";
    private DateTimeFormatter timeFormatForTest;
    @Autowired
    private HoursCalculationService tested;

    @Before
    public void setUp() throws Exception {
        timeFormatForTest = DateTimeFormat.forPattern(HOURS_FORMAT);
    }

    @Test
    public void testOnlyPriorToBedTime() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "36.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("05:00pm", timeFormatForTest),
                        DateTime.parse("08:00pm", timeFormatForTest))
        );
    }

    @Test
    public void testAfterBedTimeAndBeforeMidnight() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "32.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("08:00pm", timeFormatForTest),
                        DateTime.parse("12:00am", timeFormatForTest))
        );
    }

    @Test
    public void testOnlyAfterMidnight() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "64.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("12:00am", timeFormatForTest),
                        DateTime.parse("4:00am", timeFormatForTest))
        );
    }

    @Test(expected = RuntimeException.class)
    public void testTooEarlyBefore5PM() throws Exception {
        tested.calculateSalaryEarned(DateTime.parse("3:00pm", timeFormatForTest),
                DateTime.parse("12:00am", timeFormatForTest));
        fail("Did not catch throw/catch expected exception");
    }

    @Test(expected = RuntimeException.class)
    public void tooLateAfter4AM() throws Exception {
        tested.calculateSalaryEarned(DateTime.parse("5:00pm", timeFormatForTest),
                DateTime.parse("5:00am", timeFormatForTest));
        fail("Did not catch throw/catch expected exception");
    }

    @Test
    public void testBeforeBedTimeUntilMidnight() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "68.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("05:00pm", timeFormatForTest),
                        DateTime.parse("12:00am", timeFormatForTest))
        );
    }

    @Test
    public void testMaxTimeWorked() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "132.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("5:00pm", timeFormatForTest),
                        DateTime.parse("4:00am", timeFormatForTest))
        );
    }

    @Test
    public void testAfterBedTimeUntilEnd() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "96.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("08:00pm", timeFormatForTest),
                        DateTime.parse("4:00am", timeFormatForTest))
        );
    }

    @Test(expected = RuntimeException.class)
    public void testStartTimeAndEndTimeNotEqual() throws Exception {
        tested.calculateSalaryEarned(DateTime.parse("5:00pm", timeFormatForTest),
                DateTime.parse("5:00pm", timeFormatForTest));
        fail("Did not catch throw/catch expected exception");
    }

    @Test(expected = RuntimeException.class)
    public void testEndTimeNotBeforeStartTime() throws Exception {
        tested.calculateSalaryEarned(DateTime.parse("8:00pm", timeFormatForTest),
                DateTime.parse("5:00pm", timeFormatForTest));
        fail("Did not catch throw/catch expected exception");
    }

}
