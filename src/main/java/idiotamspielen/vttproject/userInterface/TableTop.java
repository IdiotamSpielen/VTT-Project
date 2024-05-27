package idiotamspielen.vttproject.userInterface;

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
    private final ScrollPane scrollPane;


    public TableTop() {
        backgroundLayer = new StackPane();
        tokenLayer = new StackPane();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        scrollPane = new ScrollPane();
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.prefWidthProperty().bind(widthProperty());
        scrollPane.prefHeightProperty().bind(heightProperty());

        StackPane layers = new StackPane();
        layers.getChildren().addAll(backgroundLayer, tokenLayer);
        scrollPane.setContent(layers);

        getChildren().add(scrollPane);

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
                    Path targetDirectory;

                    if (file.getName().endsWith(".jpg")) {
                        targetDirectory = Paths.get("src/library/maps");
                    }
                    else{
                        targetDirectory = Paths.get("src/library/tokens");
                    }

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
                        System.out.println("background added");
                    } else {
                        tokenLayer.getChildren().add(imageView);
                        System.out.println("Token added");
                    }

                    imageView.setOnMousePressed(e -> {
                        imageView.setUserData(new double[]{e.getSceneX(), e.getSceneY()});
                        System.out.println("Image clicked");
                    });

                    imageView.setOnMouseDragged(e -> {
                        double[] initialPosition = (double[]) imageView.getUserData();
                        double deltaX = e.getSceneX() - initialPosition[0];
                        double deltaY = e.getSceneY() - initialPosition[1];

                        imageView.setTranslateX(imageView.getTranslateX() + deltaX);
                        imageView.setTranslateY(imageView.getTranslateY() + deltaY);

                        // update saved position for next drag-event
                        imageView.setUserData(new double[]{e.getSceneX(), e.getSceneY()});
                    });

                } catch (FileNotFoundException e) {
                    System.err.println("File " + file.getName() + "could not be found: " + e.getMessage());
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