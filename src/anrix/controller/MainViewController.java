package anrix.controller;

import anrix.dao.FacultyService;
import anrix.model.Faculty;
import anrix.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;

public class MainViewController {

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

    private ObservableList<Student> Students = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        //edit_button.
        Faculty faculty =  FacultyService.getSample();

        faculty.groups.forEach(e -> Students.addAll(e.students));

        studentsList.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> studentListView) {
                return new StudentViewCellController();
            }
        });

        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsList.setItems(Students);
    }

    public void MenuHandler(Event event) {
        System.out.println(event.getTarget());
    }

    public void OnAction(ActionEvent actionEvent) {
        MenuItem mItem = (MenuItem) actionEvent.getSource();
        String side = mItem.getText();
        System.out.println("Side: " + side);


        MenuItem clickedItem = (MenuItem) actionEvent.getSource();


        System.out.println(clickedItem);


        switch (clickedItem.getText()) {
            case "New":
                System.out.println("Create new file");
                break;
            default:
                System.out.println("Кулиж идет в жопу");
        }
    }

    public void fileMenuItemClicked(ActionEvent actionEvent) {
        MenuItem clickedItem = (MenuItem) actionEvent.getSource();

        switch (clickedItem.getText()) {
            case "New":
                System.out.println("New file");
                break;
            case "Open":
                System.out.println("Open");
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                //fileChooser.showOpenDialog();
                break;
            case "Save":
                System.out.println("Save");
                break;
            case "Save as...":
                System.out.println("Save as");
                break;
            case "Exit":
                System.exit(0);
                break;
        }

    }

    public void editButtonClicked(MouseEvent mouseEvent) {

        ObservableList<Student> items = FXCollections
                .observableArrayList(
                        studentsList.getSelectionModel()
                                    .getSelectedItems());


  //      System.out.println("----------------");
        for (Student a : items) {
   //         System.out.println(a);
            Students.remove(a);
        }
  //      System.out.println("List:");
    //    Students.forEach(e -> System.out.println(e));
  //      System.out.println("End");
    }
}
