package model;
/*
 * TODO
 *
 *
 *
 */

/**
 * Pure calculations
 *
 **/
public final class PayrollCalculator {

    // Employee FICA rate (Social Security 6.2% + Medicare 1.45%) – simplified
    private static final double FICA_RATE = 0.0765;

    private PayrollCalculator() {}

    public static PayrollResult calculateFromHourly(PayrollInputs in) {
        double gross   = gross(in.hourlyRate(), in.hoursInPeriod());
        double fica    = fica(gross);
        double federal = 0.0;      // TODO: replace with real withholding
        double other   = 0.0;      // TODO: UI-driven deductions later
        double net     = gross - fica - federal - other;

        return new PayrollResult(
                in.hourlyRate(),
                in.hoursInPeriod(),
                in.schedule(),
                gross, federal, fica, other, net
        );
    }

    public static PayrollResult calculateFromAnnual(double annualSalary, PaySchedule schedule) {
        // Convert annual salary to gross pay for the selected schedule
        double gross;
        double hoursInPeriod;
        double hourlyRate;

        switch (schedule) {
            case WEEKLY -> {
                gross = annualSalary / 52.0;
                hoursInPeriod = 40;
            }
            case BI_WEEKLY -> {
                gross = annualSalary / 26.0;
                hoursInPeriod = 80;
            }
            case MONTHLY -> {
                gross = annualSalary / 12.0;
                hoursInPeriod = 173.33; // Approx 2080 hours / 12 months
            }
            default -> throw new IllegalStateException("Unexpected schedule: " + schedule);
        }

        hourlyRate = annualSalary / 2080.0; // Standard full-time year (40 hrs × 52 weeks)

        double fica    = fica(gross);
        double federal = 0.0;      // TODO: replace with real withholding
        double other   = 0.0;      // TODO: UI-driven deductions later
        double net     = gross - fica - federal - other;

        return new PayrollResult(
                hourlyRate, hoursInPeriod,
                schedule, gross, federal,
                fica, other, net
        );
    }

    public static double gross(double hourlyRate, double hoursInPeriod) {
        return hourlyRate * hoursInPeriod;
    }

    public static double fica(double gross) {
        return gross * FICA_RATE;
    }
}
