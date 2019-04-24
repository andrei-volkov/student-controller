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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;

import java.util.Optional;

import static anrix.controller.MainViewController.*;
import static javafx.scene.control.Alert.AlertType.*;
import static javafx.scene.control.Alert.AlertType.CONFIRMATION;

public class ToolBarViewController {
    @FXML
    public AnchorPane anchorPane;

    @FXML
    public Button buttonClearSearch;

    @FXML
    public JFXNodesList filterNodeList;

    @FXML
    private JFXTextField fieldSearch;

    private Alert removeStudentsAlert;
    private Alert removeGroupAlert;
    private Alert warningAlert;


    private FacultyDAO facultyDAO = ArrayListFacultyDAO.getInstance();
    private FillerService fillerService = FillerService.getInstance();

    @FXML
    public void initialize() {
        removeStudentsAlert = new Alert(CONFIRMATION);
        removeStudentsAlert.setTitle("Delete student(s)");
        removeStudentsAlert.setHeaderText("Are you sure want to remove this student(s)?");
        removeStudentsAlert.setContentText("This action is irreversible");


        removeGroupAlert = new Alert(CONFIRMATION);
        removeGroupAlert.setTitle("Delete group/faculty");
        removeGroupAlert.setHeaderText("Are you sure want to remove this group/faculty?");
        removeGroupAlert.setContentText("This action is irreversible");

        warningAlert = new Alert(ERROR);
        warningAlert.setTitle("Error");
        warningAlert.setHeaderText(null);
        warningAlert.setContentText("I can't delete all students");

        buttonClearSearch.setBackground(Background.EMPTY);
    }

    public void removeButtonClicked() {

        Optional<ButtonType> result = removeStudentsAlert.showAndWait();
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
        setRightAndGetController("/views/StudentDetailsView.fxml",
                                                    StudentDetailsViewController.class);
    }

    public void newGroupButtonClicked(MouseEvent mouseEvent) {
        setRightAndGetController("/views/NewGroupView.fxml",
                                                    NewGroupViewController.class);
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


    public void deleteGroupButtonClicked(MouseEvent mouseEvent) {

        Optional<ButtonType> result = removeGroupAlert.showAndWait();

        if (ButtonType.OK == result.get()){
            int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();

            String currentName = mainTabPane.getTabs().get(currentTabIndex).getText();
            String id = currentName.substring(2);

            if ("All students".equals(currentName)) {
                warningAlert.showAndWait();
                return;
            }

            Tab currentTab = mainTabPane
                    .getTabs()
                    .get(currentTabIndex);


            if (currentName.startsWith("G")) {
                facultyDAO.removeGroup(id);
            } else {
                facultyDAO.removeFaculty(id);
            }

            EventHandler<Event> handler = currentTab.getOnCloseRequest();

            handler.handle(new Event(EventType.ROOT) {
                @Override
                public EventTarget getSource() {
                    return currentTab;
                }
            });

            mainTabPane.getTabs().remove(currentTab);

            if (currentName.startsWith("F")) {
                for (TreeItem<String> item : mainGroupsTree.getRoot().getChildren()) {
                    if (id.equals(item.getValue())) {
                        mainGroupsTree.getRoot().getChildren().remove(item);
                        return;
                    }
                }
            } else {
                for (TreeItem<String> item : mainGroupsTree.getRoot().getChildren()) {
                    for (TreeItem<String> subitem : item.getChildren())
                        if (id.equals(subitem.getValue())) {
                            item.getChildren().remove(subitem);
                            System.out.println("deleted");
                            return;
                        }
                }
            }
        }
    }
}
