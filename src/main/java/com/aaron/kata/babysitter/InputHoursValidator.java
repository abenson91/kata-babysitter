package com.aaron.kata.babysitter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class InputHoursValidator implements ConstraintValidator<InputHoursConstraint, HoursTrackingForm> {
    private static final Integer ONE_DAY = 1;
    private static final DateTimeFormatter HOURS_TIME_FORMAT = DateTimeFormat.forPattern("hh:mma");
    private static final String EARLIEST_START_TIME = "5:00pm";
    private static final String LATEST_END_TIME = "4:00am";


    @Override
    public void initialize(InputHoursConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(HoursTrackingForm value, ConstraintValidatorContext context) {
        Boolean isValid = true;

        if (value.getStartTime() == null) {
            context.buildConstraintViolationWithTemplate(ErrorMessages.NULL_INPUT.getMessage())
                    .addPropertyNode("startTime").addConstraintViolation();
            isValid = false;
        } else if (value.getStartTime().isBefore(DateTime.parse(EARLIEST_START_TIME, HOURS_TIME_FORMAT))
                || value.getStartTime().isAfter(DateTime.parse(LATEST_END_TIME, HOURS_TIME_FORMAT).plusDays(ONE_DAY))) {
            context.buildConstraintViolationWithTemplate(ErrorMessages.OUTSIDE_INPUT_WINDOW.getMessage())
                    .addPropertyNode("startTime").addConstraintViolation();
            isValid = false;
        }

        if (value.getEndTime() == null) {
            context.buildConstraintViolationWithTemplate(ErrorMessages.NULL_INPUT.getMessage())
                    .addPropertyNode("endTime").addConstraintViolation();
            isValid = false;
        } else if (value.getEndTime().isBefore(DateTime.parse(EARLIEST_START_TIME, HOURS_TIME_FORMAT))
                || value.getEndTime().isAfter(DateTime.parse(LATEST_END_TIME, HOURS_TIME_FORMAT).plusDays(ONE_DAY))) {
            context.buildConstraintViolationWithTemplate(ErrorMessages.OUTSIDE_INPUT_WINDOW.getMessage())
                    .addPropertyNode("endTime").addConstraintViolation();
            isValid = false;
        }

        if (isValid) {
            if (!value.getEndTime().isAfter(value.getStartTime())) {
                context.buildConstraintViolationWithTemplate(ErrorMessages.END_TIME_NOT_LAST.getMessage())
                        .addPropertyNode("endTime").addConstraintViolation();
                isValid = false;
            }
        }
        return isValid;
    }


}
