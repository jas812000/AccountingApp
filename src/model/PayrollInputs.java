package model;
/*
 * TODO
 *
 *
 *
 */

/**
 * TODO
 *
 * @param hourlyRate
 * @param hoursInPeriod
 * @param schedule
 */
public record PayrollInputs(
        double hourlyRate,
        double hoursInPeriod,
        PaySchedule schedule
) {
    public PayrollInputs {
        if (hourlyRate <= 0) throw new IllegalArgumentException("hourlyRate must be > 0");
        if (hoursInPeriod < 0) throw new IllegalArgumentException("hoursInPeriod must be ≥ 0");
        if (schedule == null) throw new IllegalArgumentException("schedule is required");
    }
}
