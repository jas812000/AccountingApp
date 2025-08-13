package gui;

public enum ExpenseCategory {
    TRANSPORTATION,
    UTILITIES_AND_COMMUNICATION,
    DINING_MEALS,
    DEBT_REPAYMENT,
    PROFESSIONAL_SERVICES,
    SUBSCRIPTIONS,
    HEALTH_MEDICAL,
    HOUSEHOLD_SUPPLIES,
    PERSONAL_CARE,
    ENTERTAINMENT,
    SAVINGS_INVESTMENT,
    MISCELLANEOUS,
    HOUSING,
    GROCERIES,
    INSURANCE,
    PET_CARE,
    AFFIRM;

    @Override
    public String toString() {
        // Format enum name to readable form
        String s = name().toLowerCase().replace('_', ' ');
        // Capitalize each word
        String[] words = s.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1))
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}
