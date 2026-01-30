package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import viewmodels.TableTopViewModel
import domain.ElementType
import domain.TableTopElement
import org.jetbrains.skia.Image
import java.io.File
import kotlin.math.roundToInt

@Composable
fun TableTopView(controller: TableTopViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // Elemente zeichnen
        controller.elements.forEach { element ->
            DraggableElement(
                element = element,
                onDrag = { dragAmount ->
                    controller.moveElement(element.id, dragAmount)
                }
            )
        }

        // Import Dialog
        if (controller.pendingImportFile != null) {
            ImportDialog(
                fileName = controller.pendingImportFile?.name ?: "",
                onConfirm = { type -> controller.confirmImport(type) },
                onDismiss = { controller.cancelImport() }
            )
        }
        if (controller.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { controller.dismissError() },
                title = { Text("Fehler") },
                text = { Text(controller.errorMessage ?: "Unbekannter Fehler") },
                confirmButton = {
                    Button(onClick = { controller.dismissError() }) {
                        Text("OK")
                    }
                }
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            RecentAssetsBar(
                assets = controller.recentAssets,
                onAssetClick = { asset ->
                    controller.addFromHistory(asset)
                }
            )
        }
    }
}

@Composable
fun DraggableElement(
    element: TableTopElement,
    onDrag: (Offset) -> Unit
) {
    // Sicheres Laden des Bildes
    val bitmap = remember(element.absolutePath) {
        loadImageBitmap(File(element.absolutePath))
    }

    if (bitmap != null) {
        Image(
            bitmap = bitmap,
            contentDescription = element.name,
            contentScale = ContentScale.Fit, // Token skalieren nicht automatisch
            modifier = Modifier
                .offset { IntOffset(element.position.x.roundToInt(), element.position.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount)
                    }
                }
        )
    }
}

@Composable
fun ImportDialog(fileName: String, onConfirm: (ElementType) -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Import") },
        text = { Text("Import '$fileName' as Map or Token?") },
        confirmButton = {
            Button(onClick = { onConfirm(ElementType.TOKEN) }) { Text("Token") }
        },
        dismissButton = {
            Button(onClick = { onConfirm(ElementType.MAP) }) { Text("Map") }
        }
    )
}

fun loadImageBitmap(file: File): ImageBitmap? {
    return try {
        if (file.exists()) {
            Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap()
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}