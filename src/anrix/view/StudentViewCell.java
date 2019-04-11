package anrix.view;

import anrix.model.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class StudentViewCell extends ListCell<Student> {
    @FXML
    private Label name;

    @FXML
    private Label surname;

    @FXML
    private Label cource;

    @FXML
    private  Label group;

    @FXML
    private GridPane gridPane;

    @Override
    protected void updateItem(Student student, boolean empty) {
        super.updateItem(student, empty);
        FXMLLoader mLLoader = null;


        if(empty || student == null) {

            setText(null);
            setGraphic(null);

        } else {

            if (mLLoader == null) {
                mLLoader = new FXMLLoader(getClass().getResource("/views/StudentViewCellPage.fxml"));
                mLLoader.setController(this);

                gridPane = mLLoader.getRoot();

                try {
                    mLLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            System.out.println(name);
            name.setText(student.name);
            surname.setText(student.surname);
            group.setText("adsad");
            cource.setText("1");

            setText(null);
            setGraphic(gridPane);
        }
    }
}


