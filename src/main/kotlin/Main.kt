
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import controllers.MainController
import views.MainView
import views.TableTopState
import java.awt.Toolkit
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import kotlin.math.max

/**
 * Main entry point for the application.
 */
fun main() = application {
    val defaultMinWidth = 800.0
    val defaultMinHeight = 600.0

    val mainController = remember { MainController() }
    val tableTopState = remember { TableTopState() }
    val state = rememberWindowState().apply {
        try {
            val screenSize = Toolkit.getDefaultToolkit().screenSize
            val screenWidth = screenSize.width.toDouble()
            val screenHeight = screenSize.height.toDouble()

            val minW = screenWidth * 0.3
            val minH = screenHeight * 0.3

            val targetW = max(screenWidth / 1.5, minW)
            val targetH = max(screenHeight / 1.2, minH)

            size = DpSize(targetW.dp, targetH.dp)
        } catch (e: Exception) {
            size = DpSize(800.dp, 600.dp)
            println("Error getting screen resolution: ${e.message}")
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "VTT 0.3.0",
    ) {
        window.dropTarget = object : DropTarget() {
            override fun drop(dtde: DropTargetDropEvent) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY)
                try {
                    // Das ist pures Java - keine Compose Magie, keine Import-Fehler
                    val transferable = dtde.transferable
                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        @Suppress("UNCHECKED_CAST")
                        val files = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<File>

                        // Wir reichen die Dateien an unseren State weiter
                        tableTopState.onFilesDropped(files)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        MenuBar {
            Menu("Create") {
                Item("Spell", icon= rememberVectorPainter(Icons.Default.AutoAwesome), onClick = {
                    mainController.openSpellCreator()
                })
                Item("Item", icon = rememberVectorPainter(Icons.Default.Backpack), onClick = { })
                Separator() // Zieht eine Linie im Menü
            }
            Menu("Search") {
                Item("Spell", icon=rememberVectorPainter(Icons.AutoMirrored.Filled.ManageSearch), onClick = { mainController.openSpellSearch() })
            }
        }
        MainView(mainController)
    }
}