package anrix.controller;

import anrix.model.bean.Group;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

public class NewStudentViewController {
    @FXML
    private ComboBox<Group> comboBox;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();

    @FXML
    private void initialize() {
        ObservableList<Group> groups = FXCollections.observableArrayList();
        facultyDAO.getFaculties().forEach(f -> groups.addAll(f.groups));

        comboBox.setItems(groups);
    }
}
