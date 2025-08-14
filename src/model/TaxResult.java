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
 * @param year
 * @param gross
 * @param fica
 * @param federal
 * @param net
 */
public record TaxResult(
        int year,
        FilingStatus status,
        double gross,
        double fica,
        double federal,
        double net
) {

    // TODO: add code later
}

