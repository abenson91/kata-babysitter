package com.aaron.kata.babysitter;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

public class HoursTrackingForm {

    @NotNull(message = "Please enter a valid time in AM or PM")
    @DateTimeFormat(pattern = "hh:mma")
    @InputHoursConstraint(message = "Start time was before 5:00pm or after 4:00am")
    private DateTime startTime;

    @NotNull(message = "Please enter a valid time in AM or PM")
    @DateTimeFormat(pattern = "hh:mma")
    @InputHoursConstraint(message = "End time was before 5:00pm or after 4:00am")
    private DateTime endTime;

    public DateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    public DateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "HoursTrackingForm{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
