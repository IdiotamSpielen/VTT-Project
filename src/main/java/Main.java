import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import java.io.IOException;

public class Main extends Application {

    private static final double DEFAULT_WIDTH = 1366;
    private static final double DEFAULT_HEIGHT = 768;

    @Override
    public void start(Stage primaryStage) throws IOException {
        double width = DEFAULT_WIDTH;
        double height = DEFAULT_HEIGHT;
        try {
            Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
            width = Math.max(visualBounds.getWidth() / 2, DEFAULT_WIDTH);
            height = Math.max(visualBounds.getHeight() / 2, DEFAULT_HEIGHT);
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Bildschirmauflösung: " + e.getMessage());
        }
        setupUI(primaryStage, width, height);
    }

    private void setupUI(Stage primaryStage, double width, double height) throws IOException {
        Scene mainScene = new Scene(loadFXML(), width, height);
        primaryStage.setTitle("VTT V0.3.0");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }



    private static Parent loadFXML() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Main.fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}