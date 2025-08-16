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
 * @param gross
 * @param federal
 * @param fica
 * @param other
 * @param net
 */
public record PayrollResult(
        double hourlyRate,
        double hoursInPeriod,
        PaySchedule schedule,
        double gross,
        double federal,   // Federal placeholder
        double fica,      // SS + Medicare
        double other,     // "other" deductions
        double net
) {

    public double annualPay(){
        return hourlyRate * 2080;
    }
}

