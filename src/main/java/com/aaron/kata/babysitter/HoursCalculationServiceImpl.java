package com.aaron.kata.babysitter;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public class HoursCalculationServiceImpl implements HoursCalculationService {
    private static final DateTimeFormatter HOURS_TIME_FORMAT = DateTimeFormat.forPattern("hh:mma");
    private static final String EARLIEST_START_TIME = "5:00pm";
    private static final String LATEST_END_TIME = "4:00am";
    private static final String BED_TIME = "08:00pm";
    private static final String MIDNIGHT = "12:00am";
    private static final BigDecimal BEFORE_BED_SALARY = BigDecimal.valueOf(12.00);
    private static final BigDecimal AFTER_BED_SALARY = BigDecimal.valueOf(8.00);
    private static final BigDecimal AFTER_MIDNIGHT_SALARY = BigDecimal.valueOf(16.00);
    private static final Integer TWO_DECIMAL_PLACES = 2;
    private static final Integer DATE_ROLL_HOUR = 4;

    @Override
    public String calculateSalaryEarned(DateTime startTime, DateTime endTime) throws RuntimeException {

        if (startTime.getHourOfDay() <= DATE_ROLL_HOUR) startTime = startTime.plusDays(1);
        if (endTime.getHourOfDay() <= DATE_ROLL_HOUR) endTime = endTime.plusDays(1);
        validateStartTime(startTime);
        validateEndTime(startTime, endTime);
        Interval intervalWorked = new Interval(startTime, endTime);
        Integer hoursWorkedBeforeBed = calculateHoursWorkedBeforeBed(intervalWorked);
        Integer hoursWorkedAfterBedBeforeMidnight = calculateHoursWorkedAfterBedBeforeMidnight(intervalWorked);
        Integer hoursWorkedAfterMidnight = calculateHoursWorkedAfterMidnight(intervalWorked);
        BigDecimal calculatedWages = calculateWageFromHoursWorked(
                hoursWorkedBeforeBed,
                hoursWorkedAfterBedBeforeMidnight,
                hoursWorkedAfterMidnight
        );

        return formatWagesAsDollarValue(calculatedWages);
    }

    private void validateStartTime(DateTime startTime) throws RuntimeException {
        if (startTime.isBefore(DateTime.parse(EARLIEST_START_TIME, HOURS_TIME_FORMAT))) {
            throw new RuntimeException("Start time was before 5:00pm or after 4:00am");
        } else if (startTime.isAfter(DateTime.parse(LATEST_END_TIME, HOURS_TIME_FORMAT).plusDays(1))) {
            throw new RuntimeException("Start time was before 5:00pm or after 4:00am");
        }
    }

    private void validateEndTime(DateTime startTime, DateTime endTime) throws RuntimeException {
        if (!endTime.isAfter(startTime)) {
            throw new RuntimeException("End time was before start time");
        } else if (endTime.isAfter(DateTime.parse(LATEST_END_TIME, HOURS_TIME_FORMAT).plusDays(1))) {
            throw new RuntimeException("End time was before after 4:00am");
        }
    }

    private Integer calculateHoursWorkedBeforeBed(Interval intervalWorked) {
        Interval beforeBedInterval = new Interval(
                DateTime.parse(EARLIEST_START_TIME, HOURS_TIME_FORMAT),
                DateTime.parse(BED_TIME, HOURS_TIME_FORMAT)
        );
        return Hours.hoursIn(intervalWorked.overlap(beforeBedInterval)).getHours();
    }

    private Integer calculateHoursWorkedAfterBedBeforeMidnight(Interval intervalWorked) {
        Interval beforeMidnightInterval = new Interval(
                DateTime.parse(BED_TIME, HOURS_TIME_FORMAT),
                DateTime.parse(MIDNIGHT, HOURS_TIME_FORMAT).plusDays(1)
        );
        return Hours.hoursIn(intervalWorked.overlap(beforeMidnightInterval)).getHours();
    }

    private Integer calculateHoursWorkedAfterMidnight(Interval intervalWorked) {
        Interval afterMidnightInterval = new Interval(
                DateTime.parse(MIDNIGHT, HOURS_TIME_FORMAT).plusDays(1),
                DateTime.parse(LATEST_END_TIME, HOURS_TIME_FORMAT).plusDays(1)
        );
        return Hours.hoursIn(intervalWorked.overlap(afterMidnightInterval)).getHours();
    }

    private BigDecimal calculateWageFromHoursWorked(
            Integer hoursWorkedBeforeBed,
            Integer hoursWorkedAfterBedBeforeMidnight,
            Integer hoursWorkedAfterMidnight) {
        BigDecimal totalWage = BigDecimal.ZERO;
        totalWage = totalWage.add((BEFORE_BED_SALARY.multiply(BigDecimal.valueOf(hoursWorkedBeforeBed))));
        totalWage = totalWage.add(AFTER_BED_SALARY.multiply(BigDecimal.valueOf(hoursWorkedAfterBedBeforeMidnight)));
        totalWage = totalWage.add(AFTER_MIDNIGHT_SALARY.multiply(BigDecimal.valueOf(hoursWorkedAfterMidnight)));
        return totalWage;
    }

    private String formatWagesAsDollarValue(BigDecimal calculatedWages) {
        return calculatedWages.setScale(TWO_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP).toString();
    }
}
