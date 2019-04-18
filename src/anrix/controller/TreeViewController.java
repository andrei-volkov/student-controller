package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class TreeViewController {

    @FXML
    public TreeView groupsTree;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private TabPane tabPane;

    @FXML
    public void initialize() {
        TreeItem<Object> root = new TreeItem<>("All students");

        for (Faculty f : facultyDAO.getFaculties()) {
            TreeItem<Object> item = new TreeItem<>(f);
            for (Group g : f.groups) {
                item.getChildren().add(new TreeItem<>(g));
            }
            item.setExpanded(true);
            root.getChildren().add(item);
        }
        root.setExpanded(true);
        groupsTree.setRoot(root);


        // I'm sorry for this piece of shit((((

        groupsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Student> studentsList = FXCollections.observableArrayList();
            ListView<Student> list = new ListView<>();
            Tab tab = new Tab();

            TreeItem<Group> selectedItem = (TreeItem<Group>) newValue;
            try {
                selectedItem.getValue().number.toString(); // To test type

                studentsList.addAll(selectedItem.getValue().students);

                tab.setText(selectedItem.getValue().number.toString());

            } catch (ClassCastException e) {
                TreeItem<Faculty> facultyTreeItem = (TreeItem<Faculty>) newValue;

                try {
                    facultyTreeItem.getValue()
                            .groups
                            .forEach(g -> studentsList.addAll(g.students));

                    tab.setText(facultyTreeItem.getValue().nameOfFaculty);
                } catch (ClassCastException ex) {
                    studentsList.addAll(facultyDAO.toStudentList());
                    tab.setText("All students");
                }
           }
            MainViewController.tabContentList.add(FXCollections.observableArrayList(studentsList));

            tab.setContent(list);
            list.setCellFactory(studentListView -> new StudentViewCellController());
            list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            MainViewController.mainTabPane.getTabs().add(tab);
            list.setItems(studentsList);

            tab.setOnCloseRequest(t -> {
                // Удалять элементы из Tab content list
                Tab temp = (Tab) t.getSource();

                MainViewController
                        .tabContentList
                        .remove(MainViewController.mainTabPane.getTabs().indexOf(temp));
            });
        });
    }
}
