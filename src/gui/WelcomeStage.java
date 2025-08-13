package gui;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeStage extends Application {

    private Stage primaryStage;
    private Label titleLabel;
    private BorderPane rootLayout;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;

        // Welcome Screen
        Scene scene = new Scene(new StackPane(), 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Accounting App");
        primaryStage.show();

        showWelcomeScene();
    }

    // Menu that displays the welcome scene
    private void showWelcomeScene() {
        Label welcomeLabel = new Label("Welcome to the Accounting App!!!");
        welcomeLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        StackPane welcomePane = new StackPane(welcomeLabel);
        Scene welcomeScene = new Scene(welcomePane, 800, 600);
        primaryStage.setScene(welcomeScene);

        // 3-second delay, then displays menu
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> showMenuScene());
        pause.play();

    }

    // Menu that displays the main menu scene
    private void showMenuScene() {

        // Simple Menu Screen
        Label menuLabel = new Label("Main Menu");
        menuLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-font-family: 'Arial';");

        Button payrollButton = new Button("Payroll Calculator");
        payrollButton.setOnAction(event -> showPayrollScene());

        Button expensesButton = new Button("Expenses Calculator");
        expensesButton.setOnAction(event -> showExpensesScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> showExitScene());

        VBox menuLayout = new VBox(20, menuLabel, payrollButton, expensesButton, exitButton);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setStyle("-fx-alignment: center;");

        Scene menuScene = new Scene(menuLayout, 800, 600);
        primaryStage.setScene(menuScene);
    }

    // Menu that displays the payroll scene
    private void showPayrollScene() {
        Label title = new Label("Payroll Calculator");

        // Pay type options
        RadioButton hourlyRadio = new RadioButton("Hourly Pay");
        RadioButton annualRadio = new RadioButton("Annual Pay");
        ToggleGroup payTypeGroup = new ToggleGroup();
        hourlyRadio.setToggleGroup(payTypeGroup);
        annualRadio.setToggleGroup(payTypeGroup);
        hourlyRadio.setSelected(true);

        TextField payInput = new TextField();
        payInput.setPromptText("Enter pay amount");

        VBox payTypeBox = new VBox(10, hourlyRadio, annualRadio);
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> showMenuScene());

        VBox layout = new VBox(15, title, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-alignment: center-left;");

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
    }

    // Menu that displays the expenses scene
    private void showExpensesScene() {
        Label title = new Label("Expenses Calculator");

        ComboBox<ExpenseCategory> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll(ExpenseCategory.values());
        categoryDropdown.setPromptText("Select expense category");

        TextField descriptionInput = new TextField();
        descriptionInput.setPromptText("Expense description");

        TextField amountInput = new TextField();
        amountInput.setPromptText("Amount");

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Due date (optional)");

        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> showMenuScene());

        VBox layout = new VBox(15, title, backButton);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-alignment: center-left;");

        Scene scene = new Scene(layout, 800, 600);
        primaryStage.setScene(scene);
    }

    // Menu that displays the exit scene
    private void showExitScene() {
        Label exitLabel = new Label("Thank you for using the Accounting App.\nGood Bye!!!!");
        exitLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-font-family: 'Arial';");

        StackPane exitLayout = new StackPane(exitLabel);
        Scene exitScene = new Scene(exitLayout, 800, 600);

        primaryStage.setScene(exitScene);

        // Wait 3 seconds, then exit
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            System.out.println("Application exited.");
            Platform.exit();  // Cleanly exit JavaFX
            System.exit(0);   // Ensure JVM shuts down
        });
        pause.play();
    }

    // Main method that starts the GUI
    public static void main(String[] args) {
        launch(args);
    }

}

