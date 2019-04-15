package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Optional;

import static anrix.controller.MainViewController.mainTabPane;

public class ToolBarViewController {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    private JFXTextField fieldSearch;

    private Alert confirmAlert;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();

    @FXML
    public void initialize() {
        confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Attention!");
        confirmAlert.setHeaderText("Are you sure?");
        confirmAlert.setContentText("It will influence on database");
    }

    public void removeButtonClicked(MouseEvent mouseEvent) {

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (ButtonType.OK == result.get()){
            int selctedIndex = mainTabPane.getSelectionModel().getSelectedIndex();

            Tab selectedTab = mainTabPane
                    .getTabs()
                    .get(selctedIndex);

            ListView<Student> content = (ListView<Student>) selectedTab.getContent();

            ObservableList<Student> items = FXCollections
                    .observableArrayList(
                            content.getSelectionModel()
                                    .getSelectedItems());

            items.forEach(e -> {facultyDAO.remove(e); content.getItems().remove(e);});
        }
    }

    public void editButtonClicked(MouseEvent mouseEvent) {

    }
}
