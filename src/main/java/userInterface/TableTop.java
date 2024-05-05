package userInterface;

import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class TableTop extends StackPane {

    private final StackPane backgroundLayer;
    private final StackPane tokenLayer;
    private final ImageView imageView;


    public TableTop() {
        backgroundLayer = new StackPane();
        tokenLayer = new StackPane();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        getChildren().addAll(backgroundLayer, tokenLayer);

        ScrollPane scrollPane = new ScrollPane();
        getChildren().add(scrollPane);

        imageView = new ImageView();
        scrollPane.setContent(imageView);

        setOnDragOver(event -> {
            if (event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        setOnDragDropped(event -> {
            for (File file : event.getDragboard().getFiles()) {
                try {
                    //Determine original filepath and search for intended filepath
                    Path sourcePath = file.toPath();
                    Path targetDirectory = Paths.get("src/library/maps");
                    Path targetPath = targetDirectory.resolve(file.getName());

                    //check if target directory exists. if not, create it.
                    if (!Files.exists(targetDirectory)) {
                        Files.createDirectories(targetDirectory);
                    }

                    //Copy file into directory
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);

                } catch (IOException e) {
                    System.err.println("Error copying file " + file.getName() + " to library: " + e.getMessage());
                }
                try {
                    Image image = new Image(new FileInputStream(file));
                    ImageView imageView = new ImageView(image);

                    // Check if the file is a background image or a token
                    if (file.getName().endsWith(".jpg")) { // replace with your condition
                        backgroundLayer.getChildren().add(imageView);
                    } else {
                        tokenLayer.getChildren().add(imageView);
                    }

                    imageView.setOnMousePressed(mouseEvent -> imageView.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()}));

                    imageView.setOnMouseDragged(mouseEvent -> {
                        double[] initialPosition = (double[]) imageView.getUserData();
                        double deltaX = mouseEvent.getSceneX() - initialPosition[0];
                        double deltaY = mouseEvent.getSceneY() - initialPosition[1];

                        imageView.setTranslateX(imageView.getTranslateX() + deltaX);
                        imageView.setTranslateY(imageView.getTranslateY() + deltaY);

                        // update saved position for next drop-event
                        imageView.setUserData(new double[]{mouseEvent.getSceneX(), mouseEvent.getSceneY()});
                    });
                } catch (FileNotFoundException e) {
                    System.err.println("Datei " + file.getName() + "konnte nicht gefunden werden: " + e.getMessage());
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });
    }

    public void setDimensions(double tableTopWidth, double tableTopHeight) {
        setPrefSize(tableTopWidth, tableTopHeight);
    }
}