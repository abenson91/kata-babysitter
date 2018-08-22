package com.aaron.kata.babysitter;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Note: The @DateTimeFormat annotation is part of the spring container and cannot be tested in this Dto test. It will
 * be tested in the mvc controllers test
 */
public class HoursTrackingFormTest {
    private Validator validator;
    private HoursTrackingForm tested;

    @Before
    public void setUp() throws Exception {
        tested = new HoursTrackingForm();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTimeInputs() throws Exception {
        DateTime validTimeInput = DateTime.parse("12:30pm", DateTimeFormat.forPattern("hh:mma"));
        tested.setStartTime(validTimeInput);
        tested.setEndTime(validTimeInput);
        assertEquals("Start time was not correctly set", validTimeInput, tested.getStartTime());
        assertEquals("End time was not correctly set", validTimeInput, tested.getEndTime());
    }

    @Test
    public void testNullStartTimeInputErrorMessage() throws Exception {
        tested.setStartTime(null);
        Set<ConstraintViolation<HoursTrackingForm>> violations = validator.validate(tested);
        assertFalse("No violation occurred, expected one", violations.isEmpty());
        assertEquals("Incorrect error message",
                "Please enter a valid time in AM or PM",
                violations.iterator().next().getMessage()
        );
    }

    @Test
    public void testNullEndTimeInputErrorMessage() throws Exception {
        tested.setEndTime(null);
        Set<ConstraintViolation<HoursTrackingForm>> violations = validator.validate(tested);
        assertFalse("No violation occurred, expected one", violations.isEmpty());
        assertEquals("Incorrect error message",
                "Please enter a valid time in AM or PM",
                violations.iterator().next().getMessage()
        );
    }

}