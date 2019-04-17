package anrix.controller;

import anrix.model.bean.Group;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import static anrix.controller.MainViewController.mainWindow;

public class NewStudentViewController {
    @FXML
    public Button closeWindowButton;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField surnameTextField;

    @FXML
    public TextField facultyTextField;

    @FXML
    public TextField markTextField;

    @FXML
    public ComboBox<Group> groupComboBox;

    @FXML
    public ComboBox<String> courseComboBox;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private ObservableList<Group> groups;
    private ObservableList<String> courses;

    @FXML
    private void initialize() {
        groups = FXCollections.observableArrayList();
        courses = FXCollections.observableArrayList();

        facultyDAO.getFaculties().forEach(f -> groups.addAll(f.groups));

        for (Integer i = 1; i <= 5; i++) {
            courses.addAll(i.toString());
        }

        courseComboBox.setItems(courses);
        groupComboBox.setItems(groups);

        courseComboBox.getSelectionModel().select(0);
        groupComboBox.getSelectionModel().select(0);
    }

    public void closeButtonClicked(MouseEvent mouseEvent) {
        mainWindow.setRight(null);
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setWidth(stage.getWidth() - 250);
    }
}
