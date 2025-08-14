package gui;
/*
 * TODO
 *
 *
 *
 */

//
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * TODO
 *
 */
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

        Button taxCalculatorButton = new Button("Tax Calculator");
        taxCalculatorButton.setOnAction(event -> showTaxCalculatorScene());

        Button expensesButton = new Button("Expenses Calculator");
        expensesButton.setOnAction(event -> showExpensesScene());

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> showExitScene());

        VBox menuLayout = new VBox(20, menuLabel, payrollButton, taxCalculatorButton, expensesButton, exitButton);
        menuLayout.setPadding(new Insets(20));
        menuLayout.setStyle("-fx-alignment: center;");

        Scene menuScene = new Scene(menuLayout, 800, 600);
        primaryStage.setScene(menuScene);
    }

    // Menu that displays the payroll scene
    private void showPayrollScene() {
        PayrollCalculatorView.show(primaryStage, this::showMenuScene);
    }

    // Menu that displays the tax calculator scene
    private void showTaxCalculatorScene() {
        TaxCalculatorView.show(primaryStage, this::showMenuScene);
    }

    // Menu that displays the expenses scene
    private void showExpensesScene() {
        ExpensesView view = new ExpensesView(
                this::showMenuScene,
                this::showCalculatingPopup
        );

        Scene scene = new Scene(view, 800, 600);
        primaryStage.setScene(scene);
    }

    // Menu that displays the exit scene
    private void showExitScene() {
        ExitView.show(primaryStage);
    }

    // Creates a button to return to the Main Menu
    private Button createBackButton() {
        Button backButton = new Button("Back to Main Menu");
        backButton.setOnAction(event -> showMenuScene());
        return backButton;
    }

    private Button createCalculateButton() {
        Button calculateButton = new Button("Calculate");
        calculateButton.setOnAction(e -> showCalculatingPopup());
        return calculateButton;
    }

    private void showCalculatingPopup() {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Calculation");

        Label message = new Label("Calculating...");
        message.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox layout = new VBox(20, message);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 250, 120);
        popup.setScene(scene);

        popup.show();

        // Auto-close after 2 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(ev -> popup.close());
        pause.play();
    }


    // Main method that starts the GUI
    public static void main(String[] args) {
        launch(args);
    }

}

