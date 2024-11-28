package idiotamspielen.vttproject.userInterface

import idiotamspielen.vttproject.handlers.MouseEventHandler
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.Alert
import javafx.scene.control.ButtonBar
import javafx.scene.control.ButtonType
import javafx.scene.control.ScrollPane
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.DragEvent
import javafx.scene.input.MouseEvent
import javafx.scene.input.TransferMode
import javafx.scene.layout.StackPane
import javafx.scene.shape.Rectangle
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class TableTop : StackPane() {
    private val backgroundLayer = StackPane()
    private val tokenLayer = StackPane()
    private var grabbedNode: Node? = null


    init {
        tokenLayer.addEventFilter(
            MouseEvent.MOUSE_PRESSED
        ) { event: MouseEvent ->
            val node = event.pickResult.intersectedNode
            if (node === tokenLayer) {
                // Get the point where the mouse event occurred in the coordinate system of the backgroundLayer
                val pointInBackgroundLayer =
                    backgroundLayer.screenToLocal(event.screenX, event.screenY)

                // Check if the point is within the bounds of any child of the backgroundLayer
                val child = backgroundLayer.children.reversed().find {
                    it.boundsInParent.contains(pointInBackgroundLayer)
                }

                // Wenn ein Kind gefunden wurde, setze es als 'grabbedNode' und löse ein Ereignis aus
                child?.let {
                    grabbedNode = it
                    it.fireEvent(event.copyFor(it, it))
                }
            }
        }

        tokenLayer.addEventFilter(
            MouseEvent.MOUSE_DRAGGED
        ) { event: MouseEvent ->
            grabbedNode?.fireEvent(event.copyFor(grabbedNode, grabbedNode))
        }

        tokenLayer.addEventFilter(
            MouseEvent.MOUSE_RELEASED
        ) { event: MouseEvent? ->
            grabbedNode = null
        }

        val tokenHandler = MouseEventHandler()
        val backgroundHandler = MouseEventHandler()

        val clip = Rectangle()
        clip.widthProperty().bind(widthProperty())
        clip.heightProperty().bind(heightProperty())
        setClip(clip)

        val scrollPane = ScrollPane()
        scrollPane.hbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        scrollPane.vbarPolicy = ScrollPane.ScrollBarPolicy.NEVER
        scrollPane.prefWidthProperty().bind(widthProperty())
        scrollPane.prefHeightProperty().bind(heightProperty())

        val layers = StackPane()
        layers.children.addAll(backgroundLayer, tokenLayer)
        scrollPane.content = layers

        children.add(scrollPane)

        onDragOver = EventHandler { event: DragEvent ->
            if (event.dragboard.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY)
            }
            event.consume()
        }

        onDragDropped = EventHandler<DragEvent> { event: DragEvent ->
            for (file in event.dragboard.files) {
                try {
                    //Determine original filepath and search for intended filepath
                    val sourcePath = file.toPath()
                    var targetDirectory: Path? = null

                    val alert =
                        Alert(Alert.AlertType.CONFIRMATION)
                    alert.title = "Import"
                    alert.headerText = "Import as map or token?"

                    val buttonTypeMap = ButtonType("Map")
                    val buttonTypeToken = ButtonType("Token")
                    val buttonTypeCancel = ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE)

                    alert.buttonTypes.setAll(buttonTypeMap, buttonTypeToken, buttonTypeCancel)

                    val result = alert.showAndWait()
                    if (result.isPresent) {
                        if (result.get() == buttonTypeMap) {
                            targetDirectory =
                                Paths.get(System.getProperty("user.home"), "Documents/VTT/library/maps")

                            try {
                                val imageView = createImageView(file)
                                backgroundLayer.children.add(imageView)
                                imageView.onMousePressed = backgroundHandler
                                imageView.onMouseDragged = backgroundHandler
                            } catch (e: FileNotFoundException) {
                                System.err.println("File " + file.name + "could not be found: " + e.message)
                            }
                        } else if (result.get() == buttonTypeToken) {
                            targetDirectory =
                                Paths.get(System.getProperty("user.home"), "Documents/VTT/library/tokens")

                            try {
                                val imageView = createImageView(file)
                                tokenLayer.children.add(imageView)
                                imageView.onMousePressed = tokenHandler
                                imageView.onMouseDragged = tokenHandler
                            } catch (e: FileNotFoundException) {
                                System.err.println("File " + file.name + "could not be found: " + e.message)
                            }
                        } else {
                            // If the user cancels the dialog, skip this file
                            continue
                        }
                    } else {
                        continue  // Dialog was closed without making a choice
                    }

                    val targetPath = targetDirectory.resolve(file.name)

                    //check if target directory exists. if not, create it.
                    if (!targetDirectory?.let { Files.exists(it) }!!) {
                        Files.createDirectories(targetDirectory)
                    }

                    //Copy file into directory
                    Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING)
                } catch (e: IOException) {
                    System.err.println("Error copying file " + file.name + " to library: " + e.message)
                }
            }
            event.isDropCompleted = true
            event.consume()
        }
    }

    @Throws(FileNotFoundException::class)
    private fun createImageView(file: File): ImageView {
        val image = Image(FileInputStream(file))
        val imageView = ImageView(image)
        imageView.isFocusTraversable = true
        return imageView
    }

    fun setDimensions(tableTopWidth: Double, tableTopHeight: Double) {
        setPrefSize(tableTopWidth, tableTopHeight)
    }
}