package com.aaron.kata.babysitter;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

@InputHoursConstraint
public class HoursTrackingForm {
    private static final Integer DATE_ROLL_HOUR = 4;
    private static final Integer ONE_DAY = 1;
    @DateTimeFormat(pattern = "hh:mma")
    private DateTime startTime;
    @DateTimeFormat(pattern = "hh:mma")
    private DateTime endTime;

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        if (startTime != null && startTime.getHourOfDay() <= DATE_ROLL_HOUR) {
            this.startTime = startTime.plusDays(ONE_DAY);
        } else {
            this.startTime = startTime;
        }
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        if (endTime != null && endTime.getHourOfDay() <= DATE_ROLL_HOUR) {
            this.endTime = endTime.plusDays(ONE_DAY);
        } else {
            this.endTime = endTime;
        }
    }

    @Override
    public String toString() {
        return "HoursTrackingForm{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
