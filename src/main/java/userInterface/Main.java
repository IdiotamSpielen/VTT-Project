package userInterface;

import com.sun.istack.NotNull;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        setupUI(primaryStage);
    }

    private void setupUI(@NotNull Stage primaryStage) throws IOException {
        Scene mainScene = new Scene(loadFXML(), 1366, 768);
        primaryStage.setTitle("VTT V0.0.3");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }



    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/main.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}