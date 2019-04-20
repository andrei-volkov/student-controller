package anrix.controller;

import anrix.model.bean.Student;
import anrix.model.dao.ArrayListFacultyDAO;
import anrix.model.dao.FacultyDAO;
import anrix.model.service.FillerService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
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
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class ToolBarViewController {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Button buttonCloseSearch;

    @FXML
    public JFXNodesList filterNodeList;

    @FXML
    private JFXTextField fieldSearch;

    private Alert confirmRemoveAlert;

    private Alert confirmNewWindowAlert;

    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private FillerService fillerService = FillerService.getInstance();

    private static final int RIGHT_WINDOW_WIDTH = 250;

    @FXML
    public void initialize() {
        confirmRemoveAlert = new Alert(CONFIRMATION);
        confirmRemoveAlert.setTitle("Delete student(s)");
        confirmRemoveAlert.setHeaderText("Are you sure want to remove this student(s)?");
        confirmRemoveAlert.setContentText("This action is irreversible");

        confirmNewWindowAlert = new Alert(CONFIRMATION);
        confirmNewWindowAlert.setTitle("Rewrite window");
        confirmNewWindowAlert.setHeaderText("Right window now is open. Do you want to rewrite it?");
        confirmNewWindowAlert.setContentText("All information will be lost ");

        buttonCloseSearch.setBackground(Background.EMPTY);
    }

    public void removeButtonClicked(MouseEvent mouseEvent) {

        Optional<ButtonType> result = confirmRemoveAlert.showAndWait();
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

    public void newStudentButtonClicked(MouseEvent mouseEvent) {
        setRightWindow("/views/NewStudentView.fxml");
    }

    public void newGroupButtonClicked(MouseEvent mouseEvent) {
        setRightWindow("/views/NewGroupView.fxml");
    }

    public void clearButtonClicked(MouseEvent mouseEvent) {
        fieldSearch.clear();

        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
        ListView<Student> currentListView = getCurrentListView(currentTabIndex);

        currentListView.setItems(tabContentList.get(currentTabIndex));

    }

    public void searchFieldTextChanged(KeyEvent keyEvent) {
        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
        ListView<Student> currentListView = getCurrentListView(currentTabIndex);

        if ("".equals(fieldSearch.getText())) {
            currentListView.setItems(tabContentList.get(currentTabIndex));
            return;
        }

        currentListView.setItems(fillerService
                .findMatches(MainViewController.tabContentList.get(currentTabIndex), fieldSearch.getText()));

    }

    public void sortButtonClicked(MouseEvent mouseEvent) {
        JFXButton selectedButton = (JFXButton) mouseEvent.getSource();
        JFXButton currentButton = (JFXButton) filterNodeList.getChildren().get(0);

        if (!selectedButton.getText().equals(currentButton.getText())) {

            String currentText = currentButton.getText();
            currentButton.setText(selectedButton.getText());
            selectedButton.setText(currentText);


            int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();
            ListView<Student> currentListView = getCurrentListView(currentTabIndex);

            currentListView.setItems(fillerService
                    .sort(MainViewController.tabContentList.get(currentTabIndex),
                            currentButton.getText()));
        }
    }

    public void onFocused(MouseEvent mouseEvent) {
        filterNodeList.animateList(true);
    }

    public void onUnfocused(MouseEvent mouseEvent) {
        filterNodeList.animateList(false);
    }

    private ListView<Student> getCurrentListView(int currentTabIndex) {
        Tab currentTab = mainTabPane.getTabs().get(currentTabIndex);
        return  (ListView<Student>) currentTab.getContent();
    }

    private void setRightWindow(String resourcePath) {
        Stage stage = (Stage) mainWindow.getScene().getWindow();

        if (mainWindow.getRight() != null) {
            Optional<ButtonType> result = confirmNewWindowAlert.showAndWait();
            if (result.get() != ButtonType.OK){
                return;
            }
            stage.setWidth(stage.getWidth() - RIGHT_WINDOW_WIDTH);
        }

        stage.setWidth(stage.getWidth() + RIGHT_WINDOW_WIDTH);
        VBox content = null;

        try {
            content = FXMLLoader.load(getClass().getResource(resourcePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainWindow.setRight(content);
    }
}
