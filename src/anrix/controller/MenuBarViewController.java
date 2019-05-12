package anrix.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;

public class MenuBarViewController {
    @FXML
    public MenuBar menuBar;

    @FXML
    public Menu fileMenu;

    public void fileMenuItemClicked(ActionEvent actionEvent) throws IOException {
        MenuItem clickedItem = (MenuItem) actionEvent.getSource();

        switch (clickedItem.getText()) {
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
            case "Export db":
                final DirectoryChooser directoryChooser = new DirectoryChooser();
                File file = directoryChooser.showDialog(menuBar.getScene().getWindow());

                if(file != null) {
                    String pathToCopy = file.getAbsolutePath() + "/students.mv.db";

                    File source = new File("./students.mv.db");
                    File newFile = new File(pathToCopy);

                    Files.copy(source.toPath(), newFile.toPath());
                }
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }

    public void aboutDeveloperItemClicked() {
       try {
           Desktop.getDesktop().browse(new URI("https://github.com/CaptainMoZzilla"));
       } catch (IOException | URISyntaxException e1) {
           e1.printStackTrace();
       }
    }
}
