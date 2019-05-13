package anrix.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;

import static anrix.controller.MainViewController.mainTabPane;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.layout.Background.EMPTY;

public class BottomBarViewController {
    @FXML
    private Button scheduleButton;

    @FXML
    private FlowPane pane;

    @FXML
    private Button chartButton;

    private Alert warningAlert;

    @FXML
    private void initialize() {
        chartButton.setBackground(EMPTY);
        pane.setBackground(EMPTY);
        scheduleButton.setBackground(EMPTY);


        warningAlert = new Alert(ERROR);
        warningAlert.setTitle("Error");
        warningAlert.setHeaderText(null);

    }

    public void scheduleButtonClicked(ActionEvent actionEvent) {
        Stage stage = new Stage();

        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();

        String currentName = mainTabPane.getTabs().get(currentTabIndex).getText();
        String id = currentName.substring(2);

        if (!currentName.startsWith("G")) {
            warningAlert.setContentText("The schedule is available only for groups");
            warningAlert.showAndWait();
            return;
        }
        stage.setTitle(id);

        FXMLLoader loader = new FXMLLoader(BottomBarViewController
                .class.getResource("/views/ScheduleView.fxml"));

        try {
            stage.setScene(new Scene(loader.load(), 450, 450));
            ScheduleViewController controller = loader.getController();
            stage.show();
            controller.setURL("https://iis.bsuir.by/#/schedule;groupName=" + id);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chartButtonClicked(ActionEvent actionEvent) {
        Stage stage = new Stage();

        int currentTabIndex = mainTabPane.getSelectionModel().getSelectedIndex();

        String currentName = mainTabPane.getTabs().get(currentTabIndex).getText();
        String id = currentName.substring(2);

        if (currentName.startsWith("G")) {
            warningAlert.setContentText("The chart is available only " +
                    "for faculties and all students");
            warningAlert.showAndWait();
            return;
        }
        stage.setTitle(currentName);

        FXMLLoader loader = new FXMLLoader(BottomBarViewController
                .class.getResource("/views/ChartView.fxml"));

        try {

            stage.setScene(new Scene(loader.load(), 600, 500));
            ChartViewController controller = loader.getController();
            stage.show();
            controller.setContent(MainViewController.tabContentList.get(currentTabIndex));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
