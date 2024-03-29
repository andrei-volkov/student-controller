package anrix.controller;

import anrix.model.bean.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;

import java.io.IOException;

public class StudentViewCellController extends ListCell<Student> {
    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label cource;

    @FXML
    private  Label group;

    @FXML
    private HBox gridPane;

    @Override
    protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);
        FXMLLoader mLLoader;


        if (empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {

            mLLoader = new FXMLLoader(getClass().getResource("/views/StudentViewCell.fxml"));
            mLLoader.setController(this);

            try {
                mLLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }


            name.setText(student.name);
            surname.setText(student.surname);
            group.setText("Group: " + student.group);
            cource.setText("Gender: " + student.getGender());

            setText(null);
            setGraphic(gridPane);
        }
    }
}


