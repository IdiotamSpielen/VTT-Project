package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.PointerMatcher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.onClick
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import controllers.TableTopController
import models.ElementType
import models.TableTopElement
import java.io.File
import kotlin.math.roundToInt

@Composable
fun TableTopView(controller: TableTopController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        controller.elements.forEach { element ->
            key(element.id) {
                DraggableElement(
                    element = element,
                    onDelete = {}
                ) { dragAmount ->
                    controller.moveElement(element.id, dragAmount)
                }
            }
        }

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
                title = { Text("Error") },
                text = { Text(controller.errorMessage ?: "Unknown Error") },
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DraggableElement(
    element: TableTopElement,
    onDrag: (Offset) -> Unit,
    onDelete: () -> Unit
) {
    val bitmap = remember(element.absolutePath) {
        loadImageBitmap(File(element.absolutePath))
    }

    var showMenu by remember { mutableStateOf(false) }

    if (bitmap != null) {
        Box(
            modifier = Modifier
                .offset { IntOffset(element.position.x.roundToInt(), element.position.y.roundToInt()) }
                .pointerInput(element.id) {
                    detectTapGestures(
                        onPress = { /* Vielleicht zum Selektieren? */ },
                        onLongPress = { showMenu = true }, // Für Touch-Geräte
                        onTap = { /* Fokus setzen */ }
                    )
                }
                .onClick(
                    interactionSource = remember { MutableInteractionSource() },
                    matcher = PointerMatcher.mouse(PointerButton.Secondary),
                    onClick = {showMenu = true}
                )
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = element.name,
                modifier = Modifier
                    .pointerInput(element.id) {
                        detectDragGestures { change, dragAmount ->
                            change.consume()
                            onDrag(dragAmount)
                        }
                    }
            )

            // Ein kleines Dropdown-Menü direkt am Token
            DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                DropdownMenuItem(onClick = {
                    onDelete()
                    showMenu = false
                }) {
                    Text("Vom Board entfernen", color = Color.Red)
                }
            }
        }
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

fun loadImageBitmap(file: File): androidx.compose.ui.graphics.ImageBitmap? {
    return try {
        if (file.exists()) {
            org.jetbrains.skia.Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap()
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}