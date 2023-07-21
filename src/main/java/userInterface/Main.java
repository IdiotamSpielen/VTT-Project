package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Main extends Application {
    private static Scene mainScene;

    @Override
    public void start(Stage primaryStage) throws IOException {
        setupUI(primaryStage);
    }

    private void setupUI(Stage primaryStage) throws IOException {
        mainScene = new Scene(loadFXML("uifx"), 1366, 768);
        primaryStage.setTitle("VTT V0.0.3");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        mainScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}