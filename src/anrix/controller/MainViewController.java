package anrix.controller;

import anrix.model.bean.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;

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

    public static ArrayList<ObservableList<Student>> tabContentList = new ArrayList<>();

    public static TabPane mainTabPane;
    public static BorderPane mainWindow;
    public static TreeView mainGroupsTree;

    @FXML
    public void initialize() {
        mainTabPane = tabPane;
        mainWindow = borderPane;
        mainGroupsTree = groupsTree;
    }

}
