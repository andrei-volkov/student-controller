package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;

public class TreeViewController {

    @FXML
    public TreeView groupsTree;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private TabPane tabPane;

    @FXML
    public void initialize() throws IOException {
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


    // I'm sorry for this piese of shit((((

        URL location = getClass().getResource("/views/TabPaneView.fxml");
        FXMLLoader loader = new FXMLLoader();
        loader.setBuilderFactory(new JavaFXBuilderFactory());

        tabPane = (TabPane) loader.load(location.openStream());
        TabPaneViewController children = loader.getController();
        System.out.println(children.studentsList.getItems().size());


        System.out.println(tabPane.getTabs().size());
        //Parent root = (Parent) fxmlLoader.load(location.openStream());


        groupsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<Group> selectedItem = (TreeItem<Group>) newValue;

            System.out.println(tabPane.getTabs().size());

            try {
                // I'm sorry for this very big crutch :(((

                selectedItem.getValue().number.toString();

                ObservableList<Student> students2 = FXCollections.observableArrayList();
                students2.addAll(selectedItem.getValue().students);

                ListView<Student> list = new ListView<>();
                list.setItems(students2);
                Tab tab = new Tab();
                tab.setText(selectedItem.getValue().number.toString());
                tab.setContent(list);

                MainViewController.mainTabPane.getTabs().add(tab);
           //     children.addNewTab(tab);


            } catch (ClassCastException e) {
                TreeItem<Faculty> facultyTreeItem = (TreeItem<Faculty>) newValue;
                ObservableList<Student> students2 = FXCollections.observableArrayList();

                facultyTreeItem.getValue()
                        .groups
                        .forEach(g -> students2.addAll(g.students));


                ListView<Student> list = new ListView<>();
                list.setItems(students2);
                Tab tab = new Tab();
                tab.setText(facultyTreeItem.getValue().nameOfFaculty);
                tab.setContent(list);

                MainViewController.mainTabPane.getTabs().add(tab);
            //    root12.getTabs().add(tab);
            }
        });
    }
}
