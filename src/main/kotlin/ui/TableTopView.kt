package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.pointer.PointerButton
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
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
        val sortedElements = remember(controller.elements.toList()) {
            controller.elements.sortedBy { it.type == ElementType.TOKEN }
        }
        sortedElements.forEach { element ->
            key(element.id) {
                DraggableElement(
                    element = element,
                    tokenSize = controller.tokenSize,
                    onDelete = { controller.removeElement(element.id) },
                    onDrag = { dragAmount ->
                        controller.moveElement(element.id, dragAmount)
                    }
                )
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
    tokenSize: Float,
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
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            onDrag(dragAmount)
                        }
                    )
                }
                .pointerInput(element.id) {
                    detectTapGestures(
                        onLongPress = { showMenu = true },
                        onTap = { /* Fokus setzen */ }
                    )
                }
                .onClick(
                    interactionSource = remember { MutableInteractionSource() },
                    matcher = PointerMatcher.mouse(PointerButton.Secondary),
                    onClick = { showMenu = true }
                )
                .then(
                    if (element.type == ElementType.TOKEN) {
                        Modifier.size(tokenSize.dp)
                    } else Modifier
                )
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = element.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = if (element.type == ElementType.TOKEN) androidx.compose.ui.layout.ContentScale.Fit else androidx.compose.ui.layout.ContentScale.None
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