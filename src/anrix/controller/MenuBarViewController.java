package anrix.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;

public class MenuBarViewController {
    @FXML
    public MenuBar menuBar;

    @FXML
    public Menu fileMenu;

    public void fileMenuItemClicked(ActionEvent actionEvent) {
        MenuItem clickedItem = (MenuItem) actionEvent.getSource();

        switch (clickedItem.getText()) {
            case "New":
                System.out.println("New file");
                break;
            case "Open":
                System.out.println("Open");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                break;
            case "Save":
                System.out.println("Save");
                break;
            case "Save as...":
                System.out.println("Save as");
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }
}
