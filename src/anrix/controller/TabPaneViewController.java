package anrix.controller;

import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

import static anrix.controller.MainViewController.setRightAndGetController;

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

        studentsList.setOnMouseClicked(event -> {
            Optional<StudentDetailsViewController> optional =
                    setRightAndGetController("/views/StudentDetailsView.fxml",
                            StudentDetailsViewController.class);

            if (optional.isPresent()) {
                StudentDetailsViewController controller = optional.get();
                controller.setStudent((Student) studentsList.getSelectionModel().getSelectedItem());
            }
        });
        MainViewController.tabContentList.add(students);
    }

}