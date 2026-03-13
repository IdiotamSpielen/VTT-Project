package ui

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import viewmodels.TableTopViewmodel
import models.ElementType
import models.TableTopElement
import kotlin.math.roundToInt
import androidx.compose.ui.zIndex
import ui.components.RecentAssetsBar
import utils.rememberSmartPainter

@Composable
fun TableTopView(viewmodel: TableTopViewmodel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        val sortedElements = remember(viewmodel.elements.toList()) {
            viewmodel.elements.sortedBy { it.type == ElementType.TOKEN }
        }
        sortedElements.forEach { element ->
            key(element.id) {
                DraggableElement(
                    element = element,
                    tokenSize = viewmodel.tokenSize,
                    isEditMode = viewmodel.isEditMode,
                    onDelete = { viewmodel.removeElement(element.id) },
                    onDrag = { dragAmount ->
                        viewmodel.moveElement(element.id, dragAmount)
                    }
                )
            }
        }

        if (viewmodel.pendingImportFile != null) {
            ImportDialog(
                fileName = viewmodel.pendingImportFile?.name ?: "",
                onConfirm = { type -> viewmodel.confirmImport(type) },
                onDismiss = { viewmodel.cancelImport() }
            )
        }
        if (viewmodel.errorMessage != null) {
            AlertDialog(
                onDismissRequest = { viewmodel.dismissError() },
                title = { Text("Error") },
                text = { Text(viewmodel.errorMessage ?: "Unknown Error") },
                confirmButton = {
                    Button(onClick = { viewmodel.dismissError() }) {
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
                assets = viewmodel.recentAssets,
                onAssetClick = { asset ->
                    viewmodel.addFromHistory(asset)
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
    isEditMode: Boolean,
    onDrag: (Offset) -> Unit,
    onDelete: () -> Unit
) {
    val painter = rememberSmartPainter(element.absolutePath)
    val canDrag = remember(element.type, isEditMode) {
        element.type == ElementType.TOKEN || (element.type == ElementType.MAP && isEditMode)
    }
    var showMenu by remember { mutableStateOf(false) }

    val dragModifier = if (canDrag) {
        Modifier
            .pointerInput(element.id) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onDrag(dragAmount)
                }
            }
            .pointerHoverIcon(PointerIcon.Hand)
    } else {
        Modifier.pointerHoverIcon(PointerIcon.Default)
    }

    if (painter != null) {
        Box(
            modifier = Modifier
                .offset { IntOffset(element.position.x.roundToInt(), element.position.y.roundToInt()) }
                .zIndex(if (element.type == ElementType.TOKEN) 1f else 0f)
                .wrapContentSize()
                .then(dragModifier)
        ) {
            Image(
                painter = painter,
                contentDescription = element.name,
                modifier = if (element.type == ElementType.TOKEN) {
                    Modifier.size(tokenSize.dp)
                } else {
                    Modifier.wrapContentSize()
                },
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