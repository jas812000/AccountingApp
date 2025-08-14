package gui;
/*
 * TODO
 *
 *
 *
 */

//
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.UnaryOperator;

/**
 * TODO
 *
 */
public final class PayrollCalculatorView extends VBox {

    public PayrollCalculatorView(Runnable onBack) {
        // --- Layout scaffold ---
        setSpacing(18);
        setPadding(new Insets(32));
        setAlignment(Pos.TOP_CENTER);

        // --- Title ---
        Label title = new Label("Payroll Calculator");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        // --- Pay type (side-by-side) ---
        RadioButton hourlyRadio = new RadioButton("Hourly Pay");
        RadioButton annualRadio = new RadioButton("Annual Pay");
        ToggleGroup payTypeGroup = new ToggleGroup();
        hourlyRadio.setToggleGroup(payTypeGroup);
        annualRadio.setToggleGroup(payTypeGroup);
        hourlyRadio.setSelected(true);

        HBox payTypeRow = new HBox(16, hourlyRadio, annualRadio);
        payTypeRow.setAlignment(Pos.CENTER);

        // --- Pay schedule (radio group) ---
        RadioButton weekly   = new RadioButton("Weekly");
        RadioButton biWeekly = new RadioButton("Bi-weekly");
        RadioButton monthly  = new RadioButton("Monthly");
        ToggleGroup scheduleGroup = new ToggleGroup();
        weekly.setToggleGroup(scheduleGroup);
        biWeekly.setToggleGroup(scheduleGroup);
        monthly.setToggleGroup(scheduleGroup);
        weekly.setSelected(true);

        HBox scheduleRow = new HBox(16, new Label("Pay schedule:"), weekly, biWeekly, monthly);
        scheduleRow.setAlignment(Pos.CENTER);

        // --- Hourly inputs ---
        TextField hourlyRate = new TextField();
        hourlyRate.setPromptText("Hourly pay");
        TextField hoursInPeriod = new TextField();
        hoursInPeriod.setPromptText("Hours this pay period");
        hourlyRate.setPrefWidth(180);
        hoursInPeriod.setPrefWidth(220);

        // simple numeric formatter w/ up to 2 decimals
        UnaryOperator<TextFormatter.Change> moneyFilter = ch -> {
            String s = ch.getControlNewText();
            if (s.isEmpty()) return ch;
            if (!s.matches("\\d*(\\.\\d*)?")) return null;
            int dot = s.indexOf('.');
            if (dot >= 0 && s.length() - dot - 1 > 2) return null;
            return ch;
        };
        hourlyRate.setTextFormatter(new TextFormatter<>(moneyFilter));
        hoursInPeriod.setTextFormatter(new TextFormatter<>(moneyFilter));

        HBox hourlyInputsRow = new HBox(12, hourlyRate, hoursInPeriod);
        hourlyInputsRow.setAlignment(Pos.CENTER);

        // --- Calculate & Back ---
        Button calcBtn = new Button("Calculate");
        Button backBtn = new Button("Back to Main Menu");
        backBtn.setOnAction(e -> { if (onBack != null) onBack.run(); });

        // --- Results box ---
        Label resultsHeader = new Label("Results");
        resultsHeader.setStyle("-fx-font-weight: bold;");

        Label rHourly  = new Label("Hourly Pay: —");
        Label rSched   = new Label("Pay Schedule: —");
        Label rHours   = new Label("Hours in Period: —");
        Label rGross   = new Label("Gross Pay: —");
        Label rFed     = new Label("Federal: —");
        Label rFica    = new Label("FICA (SS + Medicare): —");
        Label rOther   = new Label("Other Deductions: —");
        Label rNet     = new Label("Net Pay: —");

        VBox resultsBox = new VBox(6,
                resultsHeader, rHourly, rSched, rHours, rGross,
                new Label("Deductions:"), rFed, rFica, rOther, rNet);
        resultsBox.setPadding(new Insets(12));
        resultsBox.setStyle("""
                -fx-border-color: #ccc;
                -fx-border-radius: 8;
                -fx-background-radius: 8;
                -fx-background-color: #fafafa;
                """);
        resultsBox.setMaxWidth(460);

        // show/hide hourly section if needed (annual path TBD)
        Runnable updateVisibility = () -> {
            boolean hourly = hourlyRadio.isSelected();
            scheduleRow.setVisible(hourly);
            scheduleRow.setManaged(hourly);
            hourlyInputsRow.setVisible(hourly);
            hourlyInputsRow.setManaged(hourly);
        };
        updateVisibility.run();
        hourlyRadio.selectedProperty().addListener((o, a, b) -> updateVisibility.run());
        annualRadio.selectedProperty().addListener((o, a, b) -> updateVisibility.run());

        // --- Calculate handler (Hourly only for now) ---
        calcBtn.setOnAction(e -> {
            if (!hourlyRadio.isSelected()) return;

            String rateText  = hourlyRate.getText().trim();
            String hoursText = hoursInPeriod.getText().trim();
            if (rateText.isBlank() || hoursText.isBlank()) return;

            try {
                double rate  = Double.parseDouble(rateText);
                double hours = Double.parseDouble(hoursText);

                model.PaySchedule schedule = weekly.isSelected() ? model.PaySchedule.WEEKLY
                        : (biWeekly.isSelected() ? model.PaySchedule.BI_WEEKLY : model.PaySchedule.MONTHLY);

                model.PayrollInputs inputs = new model.PayrollInputs(rate, hours, schedule);
                model.PayrollResult result = model.PayrollCalculator.calculate(inputs);

                NumberFormat cf = NumberFormat.getCurrencyInstance(Locale.US);

                rHourly.setText("Hourly Pay: " + cf.format(result.hourlyRate()));
                rSched.setText("Pay Schedule: " + result.schedule().displayName());
                rHours.setText("Hours in Period: " +
                        (result.hoursInPeriod() % 1 == 0
                                ? String.format("%.0f", result.hoursInPeriod())
                                : String.format("%.2f", result.hoursInPeriod())));
                rGross.setText("Gross Pay: " + cf.format(result.gross()));
                rFed.setText("Federal: —"); // cf.format(result.federal()) when implemented
                rFica.setText("FICA (SS + Medicare): " + cf.format(result.fica()));
                rOther.setText("Other Deductions: —"); // cf.format(result.other()) when used
                rNet.setText("Net Pay: " + cf.format(result.net()));

            } catch (NumberFormatException ignored) { /* show alert if desired */ }
        });

        // --- Compose page ---
        VBox page = new VBox(18, title, payTypeRow, scheduleRow, hourlyInputsRow, calcBtn, resultsBox, backBtn);
        page.setAlignment(Pos.TOP_CENTER);

        BorderPane root = new BorderPane(page);
        BorderPane.setAlignment(page, Pos.TOP_CENTER);

        getChildren().add(root);
    }

    /** Helper to display on a Stage. */
    public static void show(Stage stage, Runnable onBack) {
        PayrollCalculatorView view = new PayrollCalculatorView(onBack);
        stage.setScene(new Scene(view, 800, 600));
        stage.show();
    }
}

