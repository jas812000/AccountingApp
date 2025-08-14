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
 */
public enum PaySchedule {
    WEEKLY, BI_WEEKLY, MONTHLY;

    public String displayName() {
        return switch (this) {
            case WEEKLY -> "Weekly";
            case BI_WEEKLY -> "Bi-weekly";
            case MONTHLY -> "Monthly";
        };
    }
}

