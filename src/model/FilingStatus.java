package model;
/*
 * TODO
 *
 */

/**
 * TODO
 *
 */
public enum FilingStatus {
    SINGLE("Single"),
    MARRIED_FILING_JOINTLY("Married Filing Jointly"),
    MARRIED_FILING_SEPARATELY("Married Filing Separately"),
    HEAD_OF_HOUSEHOLD("Head of Household"),
    QUALIFYING_SURVIVING_SPOUSE("Qualifying Surviving Spouse"),
    ESTATES_AND_TRUSTS("Estates and Trusts");

    private final String display;

    FilingStatus(String display) { this.display = display; }
    public String displayName() { return display; }

    @Override public String toString() { return display; }

    /** Map from UI label back to enum */
    public static FilingStatus fromDisplay(String s) {
        for (FilingStatus fs : values()) if (fs.display.equals(s)) return fs;
        throw new IllegalArgumentException("Unknown filing status: " + s);
    }
}
