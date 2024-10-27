package idiotamspielen.vttproject.userInterface;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import idiotamspielen.vttproject.handlers.MouseEventHandler;

public class TableTop extends StackPane {

    private final StackPane backgroundLayer;
    private final StackPane tokenLayer;
    private Node grabbedNode;


    public TableTop() {
        backgroundLayer = new StackPane();
        tokenLayer = new StackPane();

        tokenLayer.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            Node node = event.getPickResult().getIntersectedNode();
            if (node == tokenLayer) {
                // Get the point where the mouse event occurred in the coordinate system of the backgroundLayer
                Point2D pointInBackgroundLayer = backgroundLayer.screenToLocal(event.getScreenX(), event.getScreenY());

                // Check if the point is within the bounds of any child of the backgroundLayer
                List<Node> children = new ArrayList<>(backgroundLayer.getChildren());
                for (int i = children.size() - 1; i >= 0; i--) {
                    Node child = children.get(i);
                    if (child.getBoundsInParent().contains(pointInBackgroundLayer)) {
                        grabbedNode = child;
                        grabbedNode.fireEvent(event.copyFor(grabbedNode, grabbedNode));
                        break;
                    }
                }
            }
        });

        tokenLayer.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (grabbedNode != null) {
                grabbedNode.fireEvent(event.copyFor(grabbedNode, grabbedNode));
            }
        });

        tokenLayer.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            grabbedNode = null;
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
                    Path targetDirectory = null;


                    String[] options = {"Map", "Token"};
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Import");
                    alert.setHeaderText("Import as map or token?");

                    ButtonType buttonTypeMap = new ButtonType("Map");
                    ButtonType buttonTypeToken = new ButtonType("Token");
                    ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(buttonTypeMap, buttonTypeToken, buttonTypeCancel);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent()) {
                        if (result.get() == buttonTypeMap) {
                            targetDirectory = Paths.get(System.getProperty("user.home"), "Documents/VTT/library/maps");

                            try {
                                ImageView imageView = createImageView(file);
                                backgroundLayer.getChildren().add(imageView);
                                imageView.setOnMousePressed(backgroundHandler);
                                imageView.setOnMouseDragged(backgroundHandler);
                            } catch (FileNotFoundException e) {
                                System.err.println("File " + file.getName() + "could not be found: " + e.getMessage());
                            }

                        } else if (result.get() == buttonTypeToken) {
                            targetDirectory = Paths.get(System.getProperty("user.home"), "Documents/VTT/library/tokens");

                            try {
                                ImageView imageView = createImageView(file);
                                tokenLayer.getChildren().add(imageView);
                                imageView.setOnMousePressed(tokenHandler);
                                imageView.setOnMouseDragged(tokenHandler);
                            } catch (FileNotFoundException e) {
                                System.err.println("File " + file.getName() + "could not be found: " + e.getMessage());
                            }

                        } else {
                            // If the user cancels the dialog, skip this file
                            continue;
                        }
                    } else {
                        continue; // Dialog was closed without making a choice
                    }

                    Path targetPath = targetDirectory.resolve(file.getName());

                    //check if target directory exists. if not, create it.
                    if (!Files.exists(targetDirectory)) {
                        Files.createDirectories(targetDirectory);
                    }

                    //Copy file into directory
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }catch (IOException e) {
                    System.err.println("Error copying file " + file.getName() + " to library: " + e.getMessage());
                }
            }
            event.setDropCompleted(true);
            event.consume();
        });

    }

    private ImageView createImageView(File file) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(file));
        ImageView imageView = new ImageView(image);
        imageView.setFocusTraversable(true);
        return imageView;
    }

    public void setDimensions(double tableTopWidth, double tableTopHeight) {
        setPrefSize(tableTopWidth, tableTopHeight);
    }
}