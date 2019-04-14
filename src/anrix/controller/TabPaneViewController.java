package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class TabPaneViewController {
    @FXML
    public ListView studentsList;

    @FXML
    public TabPane tabPane;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private ObservableList<Student> students = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        facultyDAO.getFaculties()
                .forEach(e -> e.groups
                        .forEach(a -> students.addAll(a.students)));

        studentsList.setCellFactory(studentListView -> new StudentViewCellController());
        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsList.setItems(students);
    }

    public void addNewTab(Tab tab) {
        tabPane.getTabs().add(tab);
    }

}