package anrix.controller;

import anrix.model.bean.Student;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class MainViewController {
    @FXML
    public MenuBar menuBar;

    @FXML
    public FlowPane bottomBar;

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

    public static final int RIGHT_WINDOW_WIDTH = 250;

    public static ArrayList<ObservableList<Student>> tabContentList = new ArrayList<>();

    public static TabPane mainTabPane;
    public static BorderPane mainWindow;
    public static TreeView<String> mainGroupsTree;

    private static Alert confirmNewWindowAlert;

    @FXML
    public void initialize() {
        mainTabPane = tabPane;
        mainWindow = borderPane;
        mainGroupsTree = groupsTree;


        confirmNewWindowAlert = new Alert(CONFIRMATION);
        confirmNewWindowAlert.setTitle("Rewrite window");
        confirmNewWindowAlert.setHeaderText("Right window now is open. Do you want to rewrite it?");
        confirmNewWindowAlert.setContentText("All information will be lost ");

    }


    public static <T> Optional<T> setRightAndGetController(String resourcePath, Class<T> type) {
        Stage stage = (Stage) mainWindow.getScene().getWindow();

        if (mainWindow.getRight() != null) {
            Optional<ButtonType> result = confirmNewWindowAlert.showAndWait();
            if (result.get() != ButtonType.OK){
                return Optional.empty();
            }
            stage.setWidth(stage.getWidth() - RIGHT_WINDOW_WIDTH);
        }

        stage.setWidth(stage.getWidth() + RIGHT_WINDOW_WIDTH);
        VBox content = null;

        FXMLLoader loader = new FXMLLoader(MainViewController.class.getResource(resourcePath));

        try {
            content = loader.load();

        } catch (IOException e) {
            e.printStackTrace();
        }

        mainWindow.setRight(content);

        return Optional.of(loader.getController());
    }
}
