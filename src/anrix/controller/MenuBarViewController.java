package anrix.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuBarViewController {
    @FXML
    public MenuBar menuBar;

    @FXML
    public Menu fileMenu;

    public void fileMenuItemClicked(ActionEvent actionEvent) {
        MenuItem clickedItem = (MenuItem) actionEvent.getSource();

        switch (clickedItem.getText()) {
            case "New db":
                break;
            case "Import db":
                StackPane stackPane;
                try {
                    stackPane = FXMLLoader.load(getClass().getResource("/views/NewDataBaseView.fxml"));

                    Stage stage = new Stage();
                    stage.setTitle("Open database");
                    stage.setScene(new Scene(stackPane, 500, 224));

                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Save":
                break;
            case "Save as...":
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }
}
