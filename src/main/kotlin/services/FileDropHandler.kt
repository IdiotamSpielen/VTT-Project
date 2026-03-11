package services

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.WindowScope
import java.awt.datatransfer.DataFlavor
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetAdapter
import java.awt.dnd.DropTargetDropEvent
import java.io.File
import javax.swing.JFrame

@Composable
fun WindowScope.FileDropHandler(
    onFileDrop: (File) -> Unit
) {
    LaunchedEffect(Unit) {
        val jFrame = window as? JFrame ?: return@LaunchedEffect

        val dropListener = object : DropTargetAdapter() {
            override fun drop(event: DropTargetDropEvent) {
                try {
                    event.acceptDrop(DnDConstants.ACTION_COPY)
                    val transferable = event.transferable

                    if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                        val files = transferable.getTransferData(DataFlavor.javaFileListFlavor) as List<*>
                        val firstFile = files.firstOrNull() as? File

                        if (firstFile != null) {
                            onFileDrop(firstFile)
                            event.dropComplete(true)
                        } else {
                            event.dropComplete(false)
                        }
                    } else {
                        event.rejectDrop()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    event.dropComplete(false)
                }
            }
        }

        val composeLayer = jFrame.contentPane.components.firstOrNull()

        if (composeLayer != null) {
            // Attach the drop target to the Compose rendering layer
            composeLayer.dropTarget = DropTarget(composeLayer, dropListener)
        } else {
            // Fallback to the main JFrame if the Compose layer cannot be resolved
            jFrame.dropTarget = DropTarget(jFrame, dropListener)
        }
    }
}