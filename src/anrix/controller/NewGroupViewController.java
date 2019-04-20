package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

import static anrix.controller.MainViewController.*;

public class NewGroupViewController {

    @FXML
    public ComboBox<String> typeComboBox;

    @FXML
    public ComboBox<String> facultyComboBox;

    @FXML
    public Label facultyLabel;

    @FXML
    public TextField idTextField;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();

    @FXML
    private void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("Faculty", "Group")));
        typeComboBox.getSelectionModel().select(0);

        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean checker = "Group".equals(newValue);

            facultyComboBox.setVisible(checker);
            facultyLabel.setVisible(checker);
        });

        updateFaculties();
    }

    public void closeButtonClicked() {
        mainWindow.setRight(null);
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setWidth(stage.getWidth() - RIGHT_WINDOW_WIDTH);
    }

    public void submitButtonClicked() {
        switch (typeComboBox.getSelectionModel().getSelectedItem()) {
            case "Group":
                Group group = new Group();
                group.setId(idTextField.getText());

                String facultyName = facultyComboBox.getSelectionModel().getSelectedItem();

                for (TreeItem<String> item : mainGroupsTree.getRoot().getChildren()) {
                    if (facultyName.equals(item.getValue())) {
                        item.getChildren().add(new TreeItem<>(group.id));
                    }
                }

                facultyDAO.add(group, facultyName);

                break;
            case "Faculty":
                Faculty faculty = new Faculty();
                faculty.setName(idTextField.getText());

                mainGroupsTree.getRoot().getChildren().add(new TreeItem<>(faculty.name));
                facultyDAO.add(faculty);
                break;
        }

        idTextField.clear();
        updateFaculties();
    }

    private void updateFaculties() {
        ObservableList<String> list = facultyDAO
                .getFaculties()
                .stream().map(Faculty::getName)
                .collect(Collectors
                        .toCollection(FXCollections::observableArrayList));

        facultyComboBox.setItems(list);
    }
}
