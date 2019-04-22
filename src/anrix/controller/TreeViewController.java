package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import anrix.model.service.FillerService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

import static anrix.controller.MainViewController.mainTabPane;
import static anrix.controller.MainViewController.setRightAndGetController;

public class TreeViewController {

    @FXML
    public TreeView<String> groupsTree;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private FillerService fillerService = FillerService.getInstance();

    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("All students");

        for (Faculty f : facultyDAO.getFaculties()) {
            TreeItem<String> item = new TreeItem<>(f.name);
            for (Group g : f.groups) {
                item.getChildren().add(new TreeItem<>(g.id));
            }
            item.setExpanded(true);
            root.getChildren().add(item);
        }
        root.setExpanded(true);
        groupsTree.setRoot(root);



        groupsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Student> studentsList = FXCollections.observableArrayList();
            Tab tab = new Tab();
            ListView<Student> list = new ListView<>();

            if ("All students".equals(newValue.getValue())) {
                studentsList.addAll(facultyDAO.toStudentList());
                    tab.setText("All students");

            } else if (groupsTree.getRoot().getChildren().indexOf(newValue) != -1) {
               Faculty faculty =  fillerService.findFaculty(facultyDAO.getFaculties(), newValue.getValue());

               faculty.groups
                       .forEach(g -> studentsList.addAll(g.students));

               tab.setText(faculty.name);
            } else {
                Group group = fillerService
                        .findGroup(facultyDAO.getFaculties(), newValue.getValue());

                studentsList.addAll(group.students);
                tab.setText(group.id);
            }

            MainViewController.tabContentList.add(FXCollections.observableArrayList(studentsList));
            tab.setContent(list);

            list.setCellFactory(studentListView -> new StudentViewCellController());
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            list.setItems(studentsList);

            list.setOnMouseClicked(event -> {
                Optional<StudentDetailsViewController> optional =
                        setRightAndGetController("/views/StudentDetailsView.fxml",
                                StudentDetailsViewController.class);

                if (optional.isPresent()) {
                    StudentDetailsViewController controller = optional.get();
                    controller.setStudent((Student) list.getSelectionModel().getSelectedItem());
                }
            });

            mainTabPane.getTabs().add(tab);

            tab.setOnCloseRequest(t -> {
                // Удалять элементы из Tab content list
                Tab temp = (Tab) t.getSource();

                MainViewController
                        .tabContentList
                        .remove(mainTabPane.getTabs().indexOf(temp));
            });
        });
    }
}
