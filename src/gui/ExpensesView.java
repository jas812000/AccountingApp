package gui;
/*
 * TODO
 *
 *
 *
 */

//
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import model.Expense;
import model.ExpenseCategory;
import javafx.beans.binding.Bindings;
import javafx.scene.control.TextFormatter;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;
import java.util.*;

/**
 * TODO
 *
 */
public class ExpensesView extends VBox {

    //Table that holds the expenses data
    private final ObservableList<Expense> expenses = FXCollections.observableArrayList();

    // Label that displays the running total
    private final Label totalLabel = new Label("Total: $0.00");

    /**
     * Constructor that builds the Expenses screen
     * @param onBack: Action to run when user clicks "Back to Main Menu".
     * @param onShowCalculating: Action to run when user clicks "Calculate" (shows the popup)
     */
    public ExpensesView(Runnable onBack, Runnable onShowCalculating) {

        // Configures the main VBox
        setSpacing(18);
        setPadding(new Insets(32));
        setAlignment(Pos.TOP_CENTER);

        // Screen title
        Label title = new Label("Expenses Calculator");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        // Input for entering expenses
        ComboBox<ExpenseCategory> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll(ExpenseCategory.values());
        categoryDropdown.setPromptText("Select expense category");

        // Input for description of expense
        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("model.Expense description");

        // Input for expense amount
        TextField amountInput = new TextField();
        amountInput.setPromptText("Amount");


        // after creating amountInput
        amountInput.setPrefColumnCount(12);
        amountInput.setPrefWidth(200);
        amountInput.setMaxWidth(200);
        HBox.setHgrow(amountInput, Priority.NEVER);










        UnaryOperator<TextFormatter.Change> amountFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.isEmpty()) return change;                  // allow clearing
            if (!newText.matches("\\d*(\\.\\d*)?")) return null;   // digits + optional single dot only
            int dot = newText.indexOf('.');
            if (dot >= 0 && newText.length() - dot - 1 > 2) return null; // max 2 decimals
            return change;
        };
        amountInput.setTextFormatter(new TextFormatter<>(amountFilter));












        // Date picker for expense date
        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due date (optional)");

        // Button to add the current expense fields to the table
        Button addBtn = new Button("Add expense");


        addBtn.setMinWidth(140);
        addBtn.setPrefWidth(140);

        addBtn.setDisable(true);

        addBtn.disableProperty().bind(
                categoryDropdown.getSelectionModel().selectedItemProperty().isNull()
                        .or(Bindings.createBooleanBinding(
                                () -> {
                                    String t = amountInput.getText();
                                    return t == null || t.isBlank() || !t.matches("\\d+(\\.\\d{1,2})?");
                                },
                                amountInput.textProperty()
                        ))
        );













        // When "Add expense" is clicked:
        addBtn.setOnAction(e -> {
            // 1. Trims before parsing
            String desc = descriptionInput.getText().trim();
            String amtText = amountInput.getText().trim();

            // 2. Ensures the description and amount are filled in
            if (desc.isEmpty() || amtText.isEmpty()) return;

            try {
                // 3. Parse amount as a double
                double amt = Double.parseDouble(amtText);

                // Enforces positive amount
                if (amt <= 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Amount");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a positive amount greater than zero.");
                    alert.showAndWait();
                    return;
                }

                // 4. Add a new Expense object to the table's data list
                expenses.add(new Expense(
                        categoryDropdown.getValue(), desc, amt, dueDatePicker.getValue()
                ));

                // 5. Resets all input fields for the next entry
                descriptionInput.clear();
                amountInput.clear();
                dueDatePicker.setValue(null);
                categoryDropdown.getSelectionModel().clearSelection();
                categoryDropdown.requestFocus();
            } catch (NumberFormatException ex) {
                // Create popup error alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText(
                        """
                           Please enter a valid number for the amount.
                           Examples: 1200, 1200.50, 75.25"""
                );
                // Show popup and wait until dismissed
                alert.showAndWait();

                // Refocus on amount field so user can fix input quickly
                amountInput.requestFocus();
                amountInput.selectAll();
            }
        });

        // Horizontal row containing amount input and the Add button
        HBox amountRow = new HBox(10, amountInput, addBtn);
        amountRow.setAlignment(Pos.CENTER_LEFT);
        amountRow.setFillHeight(false);





        addBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");





        // Vertical form containing all expense entry fields
        VBox form = new VBox(12,
                categoryDropdown,
                descriptionInput,
                amountRow,
                dueDatePicker);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setMaxWidth(520);

        // Table to display all added expenses
        TableView<Expense> table = new TableView<>(expenses);
        table.setPrefHeight(280);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        // Table columns: Category, Description, Amount, Due Date
        TableColumn<Expense, String> catCol = new TableColumn<>("Category");
        catCol.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(
                        c.getValue().getCategory() == null ? "" : c.getValue().getCategory().toString()
                )
        );


        TableColumn<Expense, String> descCol = new TableColumn<>("Description");
        descCol.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(c.getValue().getDescription()
                )
        );

        TableColumn<Expense, String> amtCol = new TableColumn<>("Amount");
        amtCol.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(
                        String.format("$%.2f", c.getValue().getAmount()
                        )
                )
        );

        TableColumn<Expense, String> dueCol = new TableColumn<>("Due Date");
        dueCol.setCellValueFactory(c ->
                new ReadOnlyStringWrapper(
                c.getValue().getDueDate() == null ? "" : c.getValue().getDueDate().toString()
                )
        );

        // Add all columns to the table
        table.getColumns().addAll(List.of(catCol, descCol, amtCol, dueCol));

        // Button to remove the selected expense from the table
        Button removeBtn = new Button("Remove selected");
        removeBtn.setOnAction(e -> {
            Expense sel = table.getSelectionModel().getSelectedItem();
            if (sel != null) expenses.remove(sel);
        });

        // Button to calculate the total of all expenses
        Button calculateBtn = new Button("Calculate");
        calculateBtn.setOnAction(e -> {

            // Create a format based on US currency
            NumberFormat cf = NumberFormat.getCurrencyInstance(Locale.US);
            // Calculate the sum of all expenses
            totalLabel.setText("Total: " + cf.format(
                    expenses.stream()
                            .mapToDouble(Expense::getAmount) // extracts each amount
                            .sum()));       // adds the expenses
        });

        // Button to go back to the main menu
        Button backBtn = new Button("Back to Main Menu");
        backBtn.setOnAction(e -> { if (onBack != null) onBack.run(); });

        // Style for the total label
        totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Row containing action buttons and total label
        HBox actions = new HBox(12, calculateBtn, removeBtn, totalLabel, backBtn);
        actions.setAlignment(Pos.CENTER_LEFT);

        // Main content section: form at top, table in middle, actions at bottom
        VBox content = new VBox(16, form, table, actions);
        content.setAlignment(Pos.TOP_CENTER);

        // Add the title and content to this VBoX
        getChildren().addAll(title, content);
    }
}
