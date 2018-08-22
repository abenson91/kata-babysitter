package com.aaron.kata.babysitter;

import org.joda.time.DateTime;

public interface HoursCalculationService {

    String calculateSalaryEarned(DateTime startTime, DateTime endTime);
}
