package View;

import BusinessLogic.ExerciseController;
import Model.Exercise;
import Model.MuscleGroup;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

public class ExerciseView {

    private ExerciseController exerciseController;

    public ExerciseView() {
        this.exerciseController = new ExerciseController();
    }

    public VBox getView(Stage primaryStage, Scene mainScene) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #1e1e1e;");

        TableView<Exercise> table = new TableView<>();
        table.setStyle("-fx-base: #1e1e1e; -fx-control-inner-background: #2b2b2b; -fx-table-cell-border-color: transparent; -fx-text-background-color: white;");
        TableColumn<Exercise, String> nameCol = new TableColumn<>("Exercise Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(220);
        TableColumn<Exercise, String> muscleGroupCol = new TableColumn<>("Muscle Group");
        muscleGroupCol.setCellValueFactory(new PropertyValueFactory<>("muscleGroup"));
        muscleGroupCol.setPrefWidth(170);
        table.getColumns().addAll(nameCol, muscleGroupCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        HBox buttonsBox = new HBox(15);

        buttonsBox.setAlignment(Pos.CENTER);
        Button btnCreate = new Button("Create Exercise");
        Button btnDelete = new Button("Delete Exercise");
        Button btnUpdate = new Button("Update Exercise");
        btnCreate.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-font-family: 'SansSerif'; -fx-font-size: 16px; -fx-cursor: hand;");
        btnDelete.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-font-family: 'SansSerif'; -fx-font-size: 16px; -fx-cursor: hand;");
        btnUpdate.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-font-family: 'SansSerif'; -fx-font-size: 16px; -fx-cursor: hand;");
        buttonsBox.getChildren().addAll(btnCreate, btnDelete, btnUpdate);
        Button btnBack = new Button("Back");
        btnBack.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-font-family: 'SansSerif'; -fx-font-size: 16px; -fx-cursor: hand;");
        btnBack.setOnAction(e -> primaryStage.setScene(mainScene));
        root.getChildren().addAll(table, buttonsBox, btnBack);

        ObservableList<Exercise> exercisesList = FXCollections.observableArrayList(exerciseController.getAllExercises());
        table.setItems(exercisesList);

        btnCreate.setOnAction(e -> {
            showCreateExerciseForm(table);
        });

        btnUpdate.setOnAction(e -> {
            Exercise selectedExercise = table.getSelectionModel().getSelectedItem();
            if (selectedExercise == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select an exercise!");
                return;
            }
            showUpdateExerciseForm(table, selectedExercise);
        });

        btnDelete.setOnAction(e -> {
            if (table.getSelectionModel().getSelectedItem() == null) {
                showAlert(Alert.AlertType.WARNING, "Warning", "Please select an exercise!");
                return;
            }
            try {
                Exercise selectedExercise = table.getSelectionModel().getSelectedItem();
                exerciseController.deleteExercise(selectedExercise.getId());
                refreshTable(table);
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
                return;
            }
        });

        return root;
    }

    private void refreshTable(TableView<Exercise> table) {
        ObservableList<Exercise> exercisesList = FXCollections.observableArrayList(exerciseController.getAllExercises());
        table.setItems(exercisesList);
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showCreateExerciseForm(TableView<Exercise> table) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create Exercise");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-background-color: #1e1e1e;");

        Label lblName = new Label("Exercise name:");
        lblName.setStyle("-fx-text-fill: white; -fx-font-family: 'SansSerif';");
        TextField txtName = new TextField();
        txtName.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: white;");
        Label lblMuscle = new Label("Muscle Group:");
        lblMuscle.setStyle("-fx-text-fill: white; -fx-font-family: 'SansSerif';");
        ComboBox<MuscleGroup> cbMuscle = new ComboBox<>();
        cbMuscle.getItems().addAll(exerciseController.getAllMuscleGroups());
        cbMuscle.setStyle("-fx-base: #2b2b2b;");
        cbMuscle.setPrefWidth(260);

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        buttonLayout.setPadding(new Insets(10, 0, 0, 0));
        Button btnSave = new Button("Save");
        btnSave.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-cursor: hand;");
        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-cursor: hand;");
        buttonLayout.getChildren().addAll(btnSave, btnCancel);

        btnSave.setOnAction(e -> {
            try {
                boolean success = exerciseController.createExercise(txtName.getText(), cbMuscle.getSelectionModel().getSelectedItem());
                if (success) {
                    refreshTable(table);
                    dialogStage.close();
                }
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        btnCancel.setOnAction(e -> {
            dialogStage.close();
        });

        layout.getChildren().addAll(lblName, txtName, lblMuscle, cbMuscle, buttonLayout);
        Scene scene = new Scene(layout, 300, 250);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
    }

    public void showUpdateExerciseForm(TableView<Exercise> table, Exercise selectedExercise) {
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Update Exercise");
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER_LEFT);
        layout.setStyle("-fx-background-color: #1e1e1e;");

        Label lblName = new Label("Exercise name:");
        lblName.setStyle("-fx-text-fill: white; -fx-font-family: 'SansSerif';");
        TextField txtName = new TextField(selectedExercise.getName());
        txtName.setStyle("-fx-control-inner-background: #2b2b2b; -fx-text-fill: white;");

        Label lblMuscle = new Label("Grupă Musculară:");
        lblMuscle.setStyle("-fx-text-fill: white; -fx-font-family: 'SansSerif';");
        ComboBox<MuscleGroup> cbMuscle = new ComboBox<>();
        cbMuscle.getItems().addAll(exerciseController.getAllMuscleGroups());
        cbMuscle.setValue(selectedExercise.getMuscleGroup());
        cbMuscle.setStyle("-fx-base: #2b2b2b;");
        cbMuscle.setPrefWidth(260);

        HBox buttonLayout = new HBox(10);
        buttonLayout.setAlignment(Pos.CENTER_RIGHT);
        buttonLayout.setPadding(new Insets(10, 0, 0, 0));
        Button btnUpdate = new Button("Update");
        btnUpdate.setStyle("-fx-background-color: #f57c00; -fx-text-fill: white; -fx-cursor: hand;");
        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white; -fx-cursor: hand;");
        buttonLayout.getChildren().addAll(btnUpdate, btnCancel);

        btnUpdate.setOnAction(e -> {
            try {
                boolean success = exerciseController.updateExercise(txtName.getText(), cbMuscle.getValue(), selectedExercise.getId());
                if (success) {
                    refreshTable(table);
                    dialogStage.close();
                }
            } catch (IllegalArgumentException ex) {
                showAlert(Alert.AlertType.ERROR, "Error", ex.getMessage());
            }
        });

        btnCancel.setOnAction(e -> {
            dialogStage.close();
        });

        layout.getChildren().addAll(lblName, txtName, lblMuscle, cbMuscle, buttonLayout);
        Scene scene = new Scene(layout, 300, 250);
        dialogStage.setScene(scene);
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
    }
}