import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane a = FXMLLoader.load(getClass().getResource("/views/MainView.fxml"));
        Scene scene = new Scene(a);

        stage.setTitle("Student controller");
        stage.setScene(scene);
        stage.show();
    }
}