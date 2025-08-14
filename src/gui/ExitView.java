package gui;
/*
 * TODO
 *
 *
 *
 */

//
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * TODO
 *
 */
public final class ExitView extends StackPane {

    public ExitView() {
        Label exitLabel = new Label("Thank you for using the Accounting App.\nGood Bye!!!!");
        exitLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-font-family: 'Arial';");
        setAlignment(Pos.CENTER);
        getChildren().add(exitLabel);
    }

    /** Show this exit view on a Stage and auto-quit after 3 seconds. */
    public static void show(Stage stage) {
        ExitView view = new ExitView();
        stage.setScene(new Scene(view, 800, 600));
        stage.show();

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> {
            System.out.println("Application exited.");
            Platform.exit();   // cleanly exit JavaFX
            System.exit(0);    // ensure JVM shuts down
        });
        pause.play();
    }
}

