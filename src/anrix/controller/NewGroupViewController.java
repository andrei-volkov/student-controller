package anrix.controller;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.stage.Stage;

import java.util.Arrays;

import static anrix.controller.MainViewController.mainGroupsTree;
import static anrix.controller.MainViewController.mainWindow;

public class NewGroupViewController {

    @FXML
    public ComboBox<String> typeComboBox;

    @FXML
    public ComboBox facultyComboBox;

    @FXML
    public Label facultyLabel;

    @FXML
    public TextField idTextField;

    @FXML
    private void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(Arrays.asList("Faculty", "Group")));
        typeComboBox.getSelectionModel().select(0);

        typeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean checker = "Group".equals(newValue);

            facultyComboBox.setVisible(checker);
            facultyLabel.setVisible(checker);
        });
    }

    public void closeButtonClicked() {
        mainWindow.setRight(null);
        Stage stage = (Stage) mainWindow.getScene().getWindow();
        stage.setWidth(stage.getWidth() - 250);
    }

    public void submitButtonClicked() {
        switch (typeComboBox.getSelectionModel().getSelectedItem()) {
            case "Group":
                Group group = new Group();
                group.setId(idTextField.getId());

                for (Object item : mainGroupsTree.getRoot().getChildren()) {
                    TreeItem<Faculty> treeItem = (TreeItem<Faculty>) item;
                    //if (item == )
                }

                System.out.println();

                break;
            case "Faculty":
                break;
        }

    }
}
