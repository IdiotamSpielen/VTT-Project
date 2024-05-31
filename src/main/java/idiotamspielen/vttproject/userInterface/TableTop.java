package idiotamspielen.vttproject.userInterface;

import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import idiotamspielen.vttproject.handlers.MouseEventHandler;

public class TableTop extends StackPane {

    private final StackPane backgroundLayer;
    private final StackPane tokenLayer;


    public TableTop() {
        backgroundLayer = new StackPane();
        tokenLayer = new StackPane();

        tokenLayer.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node == tokenLayer) {
                // Get the point where the mouse event occurred in the coordinate system of the backgroundLayer
                Point2D pointInBackgroundLayer = backgroundLayer.screenToLocal(event.getScreenX(), event.getScreenY());

                // Check if the point is within the bounds of any child of the backgroundLayer
                for (Node child : backgroundLayer.getChildren()) {
                    if (child.getBoundsInParent().contains(pointInBackgroundLayer)) {
                        // If the point is within the bounds of a child, fire the event on that child
                        child.fireEvent(event.copyFor(child, child));
                        break;
                    }
                }
            }
        });

        tokenLayer.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node == tokenLayer) {
                // Get the point where the mouse event occurred in the coordinate system of the backgroundLayer
                Point2D pointInBackgroundLayer = backgroundLayer.screenToLocal(event.getScreenX(), event.getScreenY());

                // Check if the point is within the bounds of any child of the backgroundLayer
                for (Node child : backgroundLayer.getChildren()) {
                    if (child.getBoundsInParent().contains(pointInBackgroundLayer)) {
                        // If the point is within the bounds of a child, fire the event on that child
                        child.fireEvent(event.copyFor(child, child));
                        break;
                    }
                }
            }
        });

        MouseEventHandler tokenHandler = new MouseEventHandler();
        MouseEventHandler backgroundHandler = new MouseEventHandler();

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        ScrollPane scrollPane = new ScrollPane();
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
                        targetDirectory = Paths.get(System.getProperty("user.home"), "Documents/library/maps");
                    }
                    else{
                        targetDirectory = Paths.get(System.getProperty("user.home"), "Documents/library/tokens");
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
                    imageView.setFocusTraversable(true);

                    // Check if the file is a background image or a token
                    if (file.getName().endsWith(".jpg")) { // replace with your condition
                        backgroundLayer.getChildren().add(imageView);
                        imageView.setOnMousePressed(backgroundHandler);
                        imageView.setOnMouseDragged(backgroundHandler);
                    } else {
                        tokenLayer.getChildren().add(imageView);
                        imageView.setOnMousePressed(tokenHandler);
                        imageView.setOnMouseDragged(tokenHandler);
                    }
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