package anrix.controller;

import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import anrix.model.service.FillerService;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

import static anrix.controller.MainViewController.*;

public class ToolBarViewController {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Button buttonCloseSearch;

    @FXML
    private JFXTextField fieldSearch;

    private Alert confirmAlert;
    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private FillerService fillerService = FillerService.getInstance();

    @FXML
    public void initialize() {
        confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Attention!");
        confirmAlert.setHeaderText("Are you sure?");
        confirmAlert.setContentText("It will influence on database");

        buttonCloseSearch.setBackground(Background.EMPTY);
    }

    public void removeButtonClicked(MouseEvent mouseEvent) {

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (ButtonType.OK == result.get()){
            int selectedIndex = mainTabPane.getSelectionModel().getSelectedIndex();

            Tab selectedTab = mainTabPane
                    .getTabs()
                    .get(selectedIndex);

            ListView<Student> content = (ListView<Student>) selectedTab.getContent();

            ObservableList<Student> items = FXCollections
                    .observableArrayList(
                            content.getSelectionModel()
                                    .getSelectedItems());

            for (Student student : items) {
                facultyDAO.remove(student);
                content.getItems().remove(student);
                tabContentList.get(selectedIndex).remove(student);
            }
        }
    }

    public void editButtonClicked(MouseEvent mouseEvent) {
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        if (null == mainWindow.getRight()) {
            stage.setWidth(stage.getWidth() + 250);
            VBox a = null;

            try {
                a = FXMLLoader.load(getClass().getResource("/views/NewStudentView.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainWindow.setRight(a);
        }
    }

    public void clearButtonClicked(MouseEvent mouseEvent) {
        fieldSearch.clear();
        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
        Tab currentTab = mainTabPane.getTabs().get(currentTabIndex);
        ListView<Student> currentListView = (ListView<Student>) currentTab.getContent();
        currentListView.setItems(tabContentList.get(currentTabIndex));

    }

    public void searchFieldTextChanged(KeyEvent keyEvent) {
        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
        Tab currentTab = mainTabPane.getTabs().get(currentTabIndex);
        ListView<Student> currentListView = (ListView<Student>) currentTab.getContent();


        if ("".equals(fieldSearch.getText())) {
            currentListView.setItems(tabContentList.get(currentTabIndex));
            return;
        }

        currentListView.setItems(fillerService
                .find(MainViewController.tabContentList.get(currentTabIndex), fieldSearch.getText()));

    }
}
