package anrix.controller;

import anrix.model.service.DatabaseService;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class NewDatabaseViewController {
    @FXML
    private Button openButton;

    @FXML
    private Label label;

    @FXML
    private StackPane content;

    private DatabaseService databaseService = DatabaseService.getInstance();

    @FXML
    private void initialize() {
        label.setFont(new Font("Arial", 22));

        content.setOnDragExited((EventHandler<Event>) event
                        -> content.setStyle("-fx-border-color: #C6C6C6;"));

        content.setOnDragOver((EventHandler<Event>) event -> mouseDragOver((DragEvent) event));

        content.setOnDragDropped((EventHandler<Event>) event -> mouseDragDropped((DragEvent) event));

        openButton.setOnMouseClicked(event -> {
            final FileChooser fileChooser = new FileChooser();

            Stage primaryStage = (Stage) MainViewController.mainWindow.getScene().getWindow();
            fileChooser
                    .getExtensionFilters()
                    .add(new FileChooser.ExtensionFilter("Db", "*.mv.db"));

            File selectedFile = fileChooser.showOpenDialog(primaryStage);

            if (null != selectedFile) {
                openDatabase(selectedFile.getAbsolutePath());
            }
        }
        );

    }

    private boolean isFileCorrect(String fileName) {
        return fileName.toLowerCase().endsWith(".mv.db");
    }

    private void mouseDragOver(final DragEvent e) {
        final Dragboard db = e.getDragboard();

        if (!isFileCorrect(db.getFiles().get(0).getName())) {
            content.setStyle(
                    "-fx-border-color: red;"
                            + "-fx-border-width: 5;"
                            + "-fx-background-color: #C6C6C6;"
                            + "-fx-border-style: solid;");

            e.acceptTransferModes(TransferMode.COPY);
        } else {
            content.setStyle(
                    "-fx-border-color: green;"
                            + "-fx-border-width: 5;"
                            + "-fx-background-color: #C6C6C6;"
                            + "-fx-border-style: solid;");
            e.acceptTransferModes(TransferMode.COPY);

        }
    }

    private void mouseDragDropped(DragEvent event) {
        final Dragboard db = event.getDragboard();

        if (db.hasFiles() && isFileCorrect(db.getFiles().get(0).getName())) {
            File file = db.getFiles().get(0);
            openDatabase(file.getAbsolutePath());
        }
    }

    private void openDatabase(String absolutePath) {

        int fileNameLength = absolutePath.length() - 6;

        try {
            databaseService.merge(absolutePath.substring(0, fileNameLength));

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Done");

            alert.setTitle("Success");
            alert.setHeaderText("Done");
            alert.setContentText("Databases merged.");

            alert.showAndWait();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}