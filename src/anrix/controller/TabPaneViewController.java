package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
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

    @FXML
    public Tab studentListTab;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private ObservableList<Student> students = facultyDAO.toStudentList();

    @FXML
    private void initialize() {
        studentsList.setCellFactory(studentListView -> new StudentViewCellController());
        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsList.setItems(students);

        MainViewController.tabContentList.add(students);
    }

}