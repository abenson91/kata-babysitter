# Babysitter Kata

## Background
This kata simulates a babysitter working and getting paid for one night.  The rules are pretty straight forward.

The babysitter:
- starts no earlier than 5:00PM
- leaves no later than 4:00AM
- gets paid $12/hour from start-time to bedtime
- gets paid $8/hour from bedtime to midnight
- gets paid $16/hour from midnight to end of job
- gets paid for full hours (no fractional hours)


## Feature
*As a babysitter<br>
In order to get paid for 1 night of work<br>
I want to calculate my nightly charge<br>*

## Assumption
Bed time starts at 8PM<br>
Time rounds to next hour if input is partial hour i.e. 5:01PM will round to 6:00PM for salary calculation<br>

## Run Instructions
Requires Maven and Java 1.8

From the command line navigate inside the kata-babysitter folder<br>
Run command: mvn spring-boot:run<br>
After output line "Started Application in x.xxx seconds" application will be running on localhost:8080/


