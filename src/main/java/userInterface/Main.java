package userInterface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import handlers.FeedbackHandler;
import handlers.SpellFileHandler;
import creators.SpellCreator;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import java.io.IOException;

public class Main extends Application {

    private FeedbackHandler feedbackHandler;
    private SpellFileHandler spellFileHandler;
    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws IOException {

        feedbackHandler = new FeedbackHandler(new Text());

        scene= new Scene(loadFXML("uifx"), 1366, 768);
        primaryStage.setTitle("VTT V0.0.3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/" + fxml + ".fxml"));
        return fxmlLoader.load(); //What is the Problem here?
    }

    public static void main(String[] args) {
        launch(args);
    }
}