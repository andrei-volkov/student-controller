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

import static anrix.controller.MainViewController.mainTabPane;

public class TreeViewController {

    @FXML
    public TreeView<String> groupsTree;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private FillerService fillerService = FillerService.getInstance();

    @FXML
    public void initialize() {
        TreeItem<String> root = new TreeItem<>("All students");

        for (Faculty f : facultyDAO.getFaculties()) {
            TreeItem<String> item = new TreeItem<>(f.nameOfFaculty);
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
            }
            else if (groupsTree.getRoot().getChildren().indexOf(newValue) != -1) {
               Faculty faculty =  fillerService.findFaculty(facultyDAO.getFaculties(), newValue.getValue());

               faculty.groups
                       .forEach(g -> studentsList.addAll(g.students));

               tab.setText(faculty.nameOfFaculty);
            } else {
                Group group = fillerService
                        .findGroup(facultyDAO.getFaculties(), newValue.getValue());

                studentsList.addAll(group.students);
                tab.setText(group.id);
            }



//            try {
//                selectedItem.getValue().id.toString(); // To test type
//
//                studentsList.addAll(selectedItem.getValue().students);
//
//                tab.setText(selectedItem.getValue().id);
//
//            } catch (ClassCastException e) {
//                TreeItem<Faculty> facultyTreeItem = (TreeItem<Faculty>) newValue;
//
//                try {
//                    facultyTreeItem.getValue()
//                            .groups
//                            .forEach(g -> studentsList.addAll(g.students));
//
//                    tab.setText(facultyTreeItem.getValue().nameOfFaculty);
//                } catch (ClassCastException ex) {
//                    studentsList.addAll(facultyDAO.toStudentList());
//                    tab.setText("All students");
//                }
//           }
            MainViewController.tabContentList.add(FXCollections.observableArrayList(studentsList));

            tab.setContent(list);
            list.setCellFactory(studentListView -> new StudentViewCellController());
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            mainTabPane.getTabs().add(tab);
            list.setItems(studentsList);

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
