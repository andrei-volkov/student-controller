package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class MainViewController {
    @FXML
    private TreeView groupsTree;

    @FXML
    private JFXTextField fieldSearch;

    @FXML
    private BorderPane borderPane;

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
    public static TabPane mainTabPane;

    @FXML
    public void initialize() {
        // TODO FIX THIS UGLY CODE (IS BETTER TO REMOVE IT))
        mainTabPane = tabPane;


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

    public void removeButtonClicked(MouseEvent mouseEvent) {

        ListView<Student>  content = (ListView<Student>) tabPane.getTabs().get(0).getContent();
        content.setItems(null);
        System.out.println(tabPane.getSelectionModel().getSelectedIndex());

        ObservableList<Student> items = FXCollections
                .observableArrayList(
                        studentsList.getSelectionModel()
                                    .getSelectedItems());

        items.forEach(e -> students.remove(e));
    }

    public void editButtonClicked(MouseEvent mouseEvent) {
        tabPane.getTabs().add(new Tab("Day"));
    }
}
