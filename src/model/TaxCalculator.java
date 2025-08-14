package model;
/*
 * TODO
 *
 *
 *
 */

import TaxTables.TaxTableCalculator2025;

/**
 * Pure calculations
 *
 **/
public final class TaxCalculator {
    private static final double FICA_RATE = 0.0765; // 6.2% + 1.45% (simplified)

    private TaxCalculator() {}

    public static TaxResult calculate(TaxInputs in) {
        double fica    = fica(in.gross());
        double federal = federal(in.year(), in.status(), in.gross()); // placeholder
        double net     = in.gross() - fica - federal;

        return new TaxResult(in.year(), in.status(), in.gross(), fica, federal, net);
    }

    /**
     * Simplified employee FICA
     * Ignores SS wage base + additional Medicare thresholds.
     **/
    public static double fica(double gross) {
        return gross * FICA_RATE;
    }

    /**
     * Placeholder for federal withholding.
     * Replace with real tables later.
     */
    public static double federal(int year, FilingStatus status, double gross) {

        // Federal withholding for 2025
        if (year == 2025) {
            return new TaxTableCalculator2025(status, gross).calculateTax();
        }
        // Default: no tax calculation available for this year
        return 0.0;
    }
}




