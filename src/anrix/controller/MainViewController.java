package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class MainViewController {
    @FXML
    public MenuBar menuBar;

    @FXML
    private AnchorPane toolBar;

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

}
