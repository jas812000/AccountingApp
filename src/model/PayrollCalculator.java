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

    public static PayrollResult calculate(PayrollInputs in) {
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

    public static double gross(double hourlyRate, double hoursInPeriod) {
        return hourlyRate * hoursInPeriod;
    }

    public static double fica(double gross) {
        return gross * FICA_RATE;
    }
}
