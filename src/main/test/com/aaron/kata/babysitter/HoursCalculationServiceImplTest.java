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
    private static final int ROLL_TO_NEXT_DAY = 1;
    private DateTimeFormatter timeFormatForTest;
    @Autowired
    private HoursCalculationService tested;

    @Before
    public void setUp() throws Exception {
        timeFormatForTest = DateTimeFormat.forPattern(HOURS_FORMAT);
    }

    @Test
    public void Calculate_InputHoursBeforeMidnight_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "36.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("05:00pm", timeFormatForTest),
                        DateTime.parse("08:00pm", timeFormatForTest))
        );
    }

    @Test
    public void Calculate_InputHoursAfterBedAndBeforeMidnight_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "32.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("08:00pm", timeFormatForTest),
                        DateTime.parse("12:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_InputAfterMidnight_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "64.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("12:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY),
                        DateTime.parse("4:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_InputBeforeBedUntilMidnight_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "68.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("05:00pm", timeFormatForTest),
                        DateTime.parse("12:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_MaxTimeWorked_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "132.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("5:00pm", timeFormatForTest),
                        DateTime.parse("4:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_AfterBedTimeUntilEndTime_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "96.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("08:00pm", timeFormatForTest),
                        DateTime.parse("4:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_WorkedPartialHourTimeRoundsUp_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "104.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("05:01pm", timeFormatForTest),
                        DateTime.parse("3:59am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }

    @Test
    public void Calculate_OnlyOneHourWorked_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "12.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("07:00pm", timeFormatForTest),
                        DateTime.parse("08:00pm", timeFormatForTest))
        );
    }

    @Test
    public void Calculate_WorkingNearPayChangeThreshold_Salary() throws Exception {
        assertEquals(
                "Calculated salary was incorrect",
                "12.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("07:00pm", timeFormatForTest),
                        DateTime.parse("08:00pm", timeFormatForTest))
        );
        assertEquals(
                "Calculated salary was incorrect",
                "8.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("08:00pm", timeFormatForTest),
                        DateTime.parse("09:00pm", timeFormatForTest))
        );
        assertEquals(
                "Calculated salary was incorrect",
                "8.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("11:00pm", timeFormatForTest),
                        DateTime.parse("12:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
        assertEquals(
                "Calculated salary was incorrect",
                "16.00",
                tested.calculateSalaryEarned(
                        DateTime.parse("12:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY),
                        DateTime.parse("01:00am", timeFormatForTest).plusDays(ROLL_TO_NEXT_DAY))
        );
    }



}
