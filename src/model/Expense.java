package model;
/*
 * TODO
 *
 *
 *
 */

//
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Represents a single expense record in the application
 * <p>
 * An expense contains:
 * <p>
 *      The category of the expense
 *      A text description of the expense
 *      The monetary amount of the expense
 *      An optional due date of the expense
 * <p>
 * This class is immutable; all fields are declared final and set
 * through the constructor, and only accessible through getters.
 */
public class Expense {

    /**
     * Core data fields for an expense record:
     * - category: the category of the expense (cannot be null)
     * - description: a short text description of the expense (nullable)
     * - amount: the monetary cost of the expense (cannot be null)
     *      - Double so null checks are possible
     * - dueDate: the date the expense is due (nullable if not applicable)
     */
    private final ExpenseCategory category;
    private final String description;
    private final Double amount;
    private final LocalDate dueDate;

    /**
     * Constructor that creates a new expense record.
     *
     * @param category                      the category of the expense; cannot be null
     * @param description                   a short description of the expense; may be null
     * @param amount                        the amount of the expense; cannot be null
     * @param dueDate                       the due date of the expense; may be null
     * @throws IllegalArgumentException     if category or amount is null
     */
    public Expense(ExpenseCategory category, String description, Double amount, LocalDate dueDate) {
        if (category == null) {
            throw new IllegalArgumentException("Category cannot be null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        this.category = category;
        this.description = description;
        this.amount = amount;
        this.dueDate = dueDate;
    }

    // Returns the category of the expense
    public ExpenseCategory getCategory() {
        return category;
    }

    // Returns the description of the expense
    public String getDescription() {
        return description;
    }

    // Returns the amount of the expense
    public double getAmount() {
        return amount;
    }

    // Returns the due date of the expense (null if not entered)
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Returns a human-readable string representation of
     * this model.Expense
     *
     * @return  a formatted string describing the expense
     */
    @Override
    public String toString() {

        // Currency formatter for US currency
        NumberFormat numFormat = NumberFormat.getCurrencyInstance(Locale.US);

        // Formats amount to no decimals (whole number) or two decimals
        boolean isWhole = amount % 1 == 0;

        // If a whole number, no decimal places; else shows exactly 2 decimals
        numFormat.setMinimumFractionDigits(isWhole ? 0 : 2);
        numFormat.setMaximumFractionDigits(isWhole ? 0 : 2);

        // Formats the amount into a string
        String amountStr = numFormat.format(amount);

        // Create formatted string, starting with category and amount
        StringBuilder sb = new StringBuilder();
        sb.append(category.name())
                .append(" | ")
                .append(amountStr);

        // Append description if provided
        if (description != null && !description.isBlank()) {
            sb.append(" | Description: ").append(description.trim());
        }

        // Append due date if provided
        if (dueDate != null) {
            sb.append(" | Due: ").append(dueDate);
        }
        return sb.toString();
    }
}
