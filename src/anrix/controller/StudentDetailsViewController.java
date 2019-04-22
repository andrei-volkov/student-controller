package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import anrix.model.service.AnimationService;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static anrix.controller.MainViewController.RIGHT_WINDOW_WIDTH;
import static anrix.controller.MainViewController.mainWindow;
import static anrix.model.bean.Student.GENDER.FEMALE;
import static anrix.model.bean.Student.GENDER.MALE;

public class StudentDetailsViewController {
    @FXML
    public Button closeWindowButton;

    @FXML
    public TextField nameTextField;

    @FXML
    public TextField surnameTextField;

    @FXML
    public TextField markTextField;

    @FXML
    public ComboBox<String> groupComboBox;

    @FXML
    public ComboBox<String> facultyComboBox;

    @FXML
    public ComboBox<String> genderComboBox;

    private Timeline markTimeline;
    private Timeline nameTimeline;
    private Timeline surnameTimeline;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private AnimationService animationService = AnimationService.getInstance();

    private ObservableList<String> groups;
    private ObservableList<String> faculties;

    @FXML
    private void initialize() {
        groups = FXCollections.observableArrayList();
        faculties = FXCollections.observableArrayList();

        faculties.addAll(facultyDAO
                .getFaculties()
                .stream()
                .map(Faculty::getName)
                .collect(Collectors.toCollection(ArrayList::new)));


        facultyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updateGroupItems();
        });

        groupComboBox.setItems(groups);
        facultyComboBox.setItems(faculties);
        genderComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("Male", "Female")));

        facultyComboBox.getSelectionModel().select(0);
        groupComboBox.getSelectionModel().select(0);
        genderComboBox.getSelectionModel().select(0);

        markTimeline = animationService.getIncorrectInputAnimation(markTextField);
        nameTimeline = animationService.getIncorrectInputAnimation(nameTextField);
        surnameTimeline = animationService.getIncorrectInputAnimation(surnameTextField);

        updateGroupItems();
    }


    public void closeButtonClicked(MouseEvent mouseEvent) {
        closeWindow();
    }

    public void submitButtonClicked() {

        Student student;

        try {
            Double mark = Double.parseDouble(markTextField.getText());
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();


            if (name.length() == 0) {
                nameTimeline.play();
                return;
            }

            if (surname.length() == 0) {
                surnameTimeline.play();
                return;
            }

            if (mark > 10 || mark < 0)
                throw new IllegalArgumentException();


            student = new Student(name,
                                surname,
                                groupComboBox.getSelectionModel().getSelectedItem(),
                                facultyComboBox.getSelectionModel().getSelectedItem(),
                                mark,
                                "Male".equals(genderComboBox.getSelectionModel()
                                        .getSelectedItem()) ? MALE : FEMALE);

        } catch (IllegalArgumentException e) {
            markTimeline.play();
            return;
        }

        facultyDAO.add(student);
        closeWindow();
    }

    private void closeWindow() {
        mainWindow.setRight(null);
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setWidth(stage.getWidth() - RIGHT_WINDOW_WIDTH);
    }

    private void updateGroupItems() {
        groups.clear();

        groups.addAll(facultyDAO.getFaculties()
                .stream()
                .filter(f -> f.getName().equals(facultyComboBox
                        .getSelectionModel().getSelectedItem()))
                .findFirst()
                .get()
                .getGroups()
                .stream()
                .map(Group::getId)
                .collect(Collectors.toCollection(ArrayList::new)));
        groupComboBox.getSelectionModel().select(0);
    }

    public void setStudent(Student student) {
        nameTextField.setText(student.name);
        surnameTextField.setText(student.surname);
        markTextField.setText(student.averageMark.toString());

        int facultyIndex = faculties.indexOf(student.getFaculty());
        facultyComboBox.getSelectionModel().select(facultyIndex);

        updateGroupItems();

        int groupIndex = groupComboBox.getItems().indexOf(student.getGroup());
        groupComboBox.getSelectionModel().select(groupIndex);

        genderComboBox.getSelectionModel().select(student.getGender().equals(MALE) ? 0 : 1);
    }
}
