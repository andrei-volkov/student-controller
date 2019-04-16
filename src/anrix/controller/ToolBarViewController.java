package anrix.controller;

import anrix.dao.ArrayListFacultyDAO;
import anrix.dao.FacultyDAO;
import anrix.model.Student;
import anrix.service.FillerService;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.util.Optional;

import static anrix.controller.MainViewController.mainTabPane;

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
            int selctedIndex = mainTabPane.getSelectionModel().getSelectedIndex();

            Tab selectedTab = mainTabPane
                    .getTabs()
                    .get(selctedIndex);

            ListView<Student> content = (ListView<Student>) selectedTab.getContent();

            ObservableList<Student> items = FXCollections
                    .observableArrayList(
                            content.getSelectionModel()
                                    .getSelectedItems());

            items.forEach(e -> {facultyDAO.remove(e); content.getItems().remove(e);});
        }
    }

    public void editButtonClicked(MouseEvent mouseEvent) {

    }

    public void clearButtonClicked(MouseEvent mouseEvent) {
        fieldSearch.clear();
    }

    public void searchFieldTextChanged(KeyEvent keyEvent) {
        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
        Tab currentTab = mainTabPane.getTabs().get(currentTabIndex);
        ListView<Student> currentListView = (ListView<Student>) currentTab.getContent();


        currentListView.setItems(fillerService
                .find(MainViewController.tabContentList.get(currentTabIndex), fieldSearch.getText()));

    }
}
