package View;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class MainWindow extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(25);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e1e;");
        Label titleLabel = new Label("GYM TRACKER");
        titleLabel.setFont(Font.font("SansSerif", FontWeight.BOLD, 36));
        titleLabel.setStyle("-fx-text-fill: white; -fx-padding: 0 0 30 0;");
        Button btnSeeWorkouts = createStyledButton("See Workouts");
        Button btnLogWorkout = createStyledButton("Log New Workout");
        Button btnSeeExercises = createStyledButton("See Exercises");
        root.getChildren().addAll(titleLabel, btnSeeWorkouts, btnLogWorkout, btnSeeExercises);
        Scene scene = new Scene(root, 600, 550);
        btnSeeWorkouts.setOnAction(e -> {
            System.out.println("Se deschide lista de antrenamente...");
            // Aici vei apela controllerul sau vei schimba Scena
        });

        btnLogWorkout.setOnAction(e -> {
            System.out.println("Se deschide Log New Workout...");
        });

        btnSeeExercises.setOnAction(e -> {
            ExerciseView exerciseView = new ExerciseView();
            VBox exerciseLayout = exerciseView.getView(primaryStage,scene);
            Scene exerciseScene = new Scene(exerciseLayout, 600, 550);
            primaryStage.setScene(exerciseScene);
        });
        primaryStage.setTitle("Gym Tracker");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Metodă utilitară pentru a aplica designul pe butoane folosind JavaFX CSS
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);

        // CSS specific JavaFX pentru stilizare
        String defaultStyle = "-fx-background-color: #3c3f41; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'SansSerif'; " +
                "-fx-font-size: 20px; " +
                "-fx-pref-width: 280px; " +
                "-fx-pref-height: 50px; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;";

        String hoverStyle = "-fx-background-color: #4c5052; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'SansSerif'; " +
                "-fx-font-size: 20px; " +
                "-fx-pref-width: 280px; " +
                "-fx-pref-height: 50px; " +
                "-fx-background-radius: 8px; " +
                "-fx-cursor: hand;";

        button.setStyle(defaultStyle);


        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(defaultStyle));

        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}