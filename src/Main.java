import anrix.model.Student;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Student student = new Student("asd", "asd", 2d);
        Student student2 = new Student("asd", "asd", 2d);
        System.out.println(student == student2);
        System.out.println(student.equals(student2));


        BorderPane a = FXMLLoader.load(getClass().getResource("/views/main.fxml"));
       // a.getLeft()

        Scene scene = new Scene(a);

        stage.setTitle("Layout Demo");
        stage.setScene(scene);
        stage.show();
    }


    public BorderPane createBorderPane() {

        BorderPane borderPane = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");

        fileMenu.getItems().addAll(
                new MenuItem("New"),
                new SeparatorMenuItem(),
                new MenuItem("Open"),
                new MenuItem("Save"),
                new MenuItem("Save As..."),
                new SeparatorMenuItem(),
                new MenuItem("Exit"));

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().addAll(
                new MenuItem("Undo"),
                new MenuItem("Redo"),
                new MenuItem("Cut"),
                new MenuItem("Copy"),
                new MenuItem("Paste"),
                new SeparatorMenuItem(),
                new MenuItem("Search/Replace"));

        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().addAll(
                new MenuItem("Help Contents"),
                new SeparatorMenuItem(),
                new MenuItem("About..."));
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        ToolBar toolbar = new ToolBar(
                new Button("New"),
                new Button("Open"),
                new Separator(),
                new Button("Cut"),
                new Button("Copy"),
                new Button("Paste"));

        VBox vbox = new VBox();
        vbox.getChildren().addAll(menuBar, toolbar);

        TreeItem<String> ti = new TreeItem<>("Projects");
        ti.getChildren().addAll(
                new TreeItem<String>("Project 1"),
                new TreeItem<String>("Project 2"),
                new TreeItem<String>("Project 3"),
                new TreeItem<String>("Project 4"));
        TreeView<String> tv = new TreeView<String>(ti);

        TabPane tabPaneLeft = new TabPane();

        tabPaneLeft.tabClosingPolicyProperty().setValue(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPaneLeft.setRotateGraphic(true);
        tabPaneLeft.sideProperty().setValue(Side.LEFT);

        Tab tab1 = new Tab("Project List");
        tab1.setContent(tv);
        tabPaneLeft.getTabs().addAll(tab1, new Tab("Explorer"));

        TabPane tabPaneRight = new TabPane();

        tabPaneRight.getTabs().addAll(new Tab("Outline"),
                new Tab("Task List"));

        borderPane.setTop(vbox);
        borderPane.setLeft(tabPaneLeft);
        borderPane.setCenter(new TextArea());
        borderPane.setRight(tabPaneRight);
        borderPane.setBottom(new Label("Status text: Borderpane demo"));

        return borderPane;
    }
}