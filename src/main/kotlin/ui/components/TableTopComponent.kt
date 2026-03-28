package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.zIndex
import models.*
import ui.components.RecentAssetsBar
import utils.rememberSmartPainter
import viewmodels.TableTopViewmodel
import kotlin.math.roundToInt

@Composable
fun TableTopView(viewmodel: TableTopViewmodel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        // Render visible layers in order
        viewmodel.currentFloor.layers.filter { it.isVisible }.forEachIndexed { layerIndex, layer ->
            // Use elements directly from the layer for better reactivity
            layer.elements.forEach { element ->
                key(element.id) {
                    DraggableElement(
                        element = element,
                        tokenSize = viewmodel.tokenSize,
                        isEditMode = viewmodel.isEditMode,
                        modifier = Modifier.zIndex(layerIndex.toFloat()),
                        onDelete = { viewmodel.removeElement(element.id) },
                        onDrag = { dragAmount ->
                            viewmodel.moveElement(element.id, dragAmount)
                        }
                    )
                }
            }
        }

        // Layer and Floor Controls
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .background(Color.Black.copy(alpha = 0.6f))
                .padding(8.dp)
        ) {
            Text("Floors", color = Color.White, style = MaterialTheme.typography.subtitle2)
            viewmodel.currentMap.floors.forEachIndexed { index, floor ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewmodel.currentFloorIndex == index,
                        onClick = { viewmodel.currentFloorIndex = index }
                    )
                    Text(floor.name, color = Color.White)
                }
            }
            Button(onClick = { viewmodel.addFloor() }, modifier = Modifier.padding(top = 4.dp)) {
                Text("Add Floor")
            }
            
            Button(onClick = { viewmodel.saveCurrentMap() }, modifier = Modifier.padding(top = 8.dp)) {
                Text("Save Map")
            }
            
            Spacer(Modifier.height(8.dp))
            
            Text("Active Layer", color = Color.White, style = MaterialTheme.typography.subtitle2)
            LayerType.entries.forEach { type ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = viewmodel.activeLayerType == type,
                        onClick = { viewmodel.activeLayerType = type }
                    )
                    Text(type.name, color = Color.White)
                }
            }
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
    modifier: Modifier = Modifier,
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
            modifier = modifier
                .offset { IntOffset(element.position.x.roundToInt(), element.position.y.roundToInt()) }
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
            if (showMenu) {
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
}
