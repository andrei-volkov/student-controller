package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
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

import java.util.Arrays;

import static anrix.controller.MainViewController.RIGHT_WINDOW_WIDTH;
import static anrix.controller.MainViewController.mainWindow;
import static anrix.model.bean.Student.GENDER.FEMALE;
import static anrix.model.bean.Student.GENDER.MALE;

public class NewStudentViewController {
    @FXML
    public Button closeWindowButton;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField surnameTextField;

    @FXML
    public TextField markTextField;

    @FXML
    public ComboBox<Group> groupComboBox;

    @FXML
    public ComboBox<String> courseComboBox;

    @FXML
    public ComboBox<Faculty> facultyComboBox;

    @FXML
    public ComboBox<String> genderComboBox;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private ObservableList<Group> groups;
    private ObservableList<String> courses;
    private ObservableList<Faculty> faculties;

    @FXML
    private void initialize() {
        groups = FXCollections.observableArrayList();
        courses = FXCollections.observableArrayList();
        faculties = FXCollections.observableArrayList();

        faculties.addAll(facultyDAO.getFaculties());
        groups.addAll(facultyDAO.getFaculties()
                    .stream()
                    .filter(f -> f.getName().equals(faculties.get(0).getName()))
                    .findFirst()
                    .get()
                    .getGroups());

        facultyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            groups.clear();
            groups.addAll(facultyDAO.getFaculties()
                    .stream()
                    .filter(f -> f.getName().equals(newValue.getName()))
                    .findFirst()
                    .get()
                    .getGroups());
            groupComboBox.getSelectionModel().select(0);
        });


        for (int i = 1; i <= 5; i++) {
            courses.addAll(Integer.toString(i));
        }

        courseComboBox.setItems(courses);
        groupComboBox.setItems(groups);
        facultyComboBox.setItems(faculties);
        genderComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("Male", "Female")));

        facultyComboBox.getSelectionModel().select(0);
        courseComboBox.getSelectionModel().select(0);
        groupComboBox.getSelectionModel().select(0);
        genderComboBox.getSelectionModel().select(0);



    }

    public void closeButtonClicked(MouseEvent mouseEvent) {
        closeWindow();
    }

    public void submitButtonClicked() {

        Student student = new Student(nameTextField.getText(),
                surnameTextField.getText(),
                groupComboBox.getSelectionModel().getSelectedItem().getId(),
                courseComboBox.getSelectionModel().getSelectedItem(),
                facultyComboBox.getSelectionModel().getSelectedItem().getName(),
                Double.parseDouble(markTextField.getText()),
                "Male".equals(genderComboBox.getSelectionModel().getSelectedItem()) ? MALE : FEMALE );

        facultyDAO.add(student);
        closeWindow();
    }

    private void closeWindow() {
        mainWindow.setRight(null);
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setWidth(stage.getWidth() - RIGHT_WINDOW_WIDTH);
    }
}
