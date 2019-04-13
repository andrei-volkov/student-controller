package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.awt.*;

public class MainViewController {
    @FXML
    public TreeView groupsTree;

    @FXML
    public JFXTextField fieldSearch;

    @FXML
    private javafx.scene.control.Menu fileMenu;

    @FXML
    private javafx.scene.control.Menu optionsMenu;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button edit_button;

    @FXML
    private ListView<Student> studentsList;

    private ObservableList<Student> students = FXCollections.observableArrayList();
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();


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


        // TODO FIX THIS UGLY CODE (IS BETTER TO REMOVE IT))

        groupsTree.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            TreeItem<Group> selectedItem = (TreeItem<Group>) newValue;
            try {
                selectedItem.getValue().number.toString();
                ObservableList<Student> students2 = FXCollections.observableArrayList();
                selectedItem.getValue()
                        .students
                        .forEach(e-> students2.add(e));

                ListView<Student> list = new ListView<>();
                list.setItems(students2);
                Tab tab = new Tab();
                tab.setText(selectedItem.getValue().number.toString());
                tab.setContent(list);
                tabPane.getTabs().add(tab);

            } catch (ClassCastException e) {
                TreeItem<Faculty> facultyTreeItem = (TreeItem<Faculty>) newValue;
                ObservableList<Student> students2 = FXCollections.observableArrayList();

                facultyTreeItem.getValue()
                        .groups
                        .forEach(g -> g.students
                                .forEach(s -> students2.add(s)));


                ListView<Student> list = new ListView<>();
                list.setItems(students2);
                Tab tab = new Tab();
                tab.setText(facultyTreeItem.getValue().nameOfFaculty);
                tab.setContent(list);
                tabPane.getTabs().add(tab);
            }
        });

        facultyDAO.getFaculties()
                .forEach(e -> e.groups
                        .forEach(a -> a.students.forEach(c -> students.add(c))));

        studentsList.setCellFactory(studentListView -> new StudentViewCellController());
        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsList.setItems(students);
    }

    public void MenuHandler(Event event) {
        System.out.println(event.getTarget());
    }

    public void fileMenuItemClicked(ActionEvent actionEvent) {
        MenuItem clickedItem = (MenuItem) actionEvent.getSource();

        switch (clickedItem.getText()) {
            case "New":
                System.out.println("New file");
                break;
            case "Open":
                System.out.println("Open");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                break;
            case "Save":
                System.out.println("Save");
                break;
            case "Save as...":
                System.out.println("Save as");
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }

    public void editButtonClicked(MouseEvent mouseEvent) {

        ListView<Student>  content = (ListView<Student>) tabPane.getTabs().get(0).getContent();
        content.setItems(null);
        System.out.println(tabPane.getSelectionModel().getSelectedIndex());

        ObservableList<Student> items = FXCollections
                .observableArrayList(
                        studentsList.getSelectionModel()
                                    .getSelectedItems());

        items.forEach(e -> students.remove(e));
    }
}
