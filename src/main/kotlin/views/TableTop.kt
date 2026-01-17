package views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import kotlin.math.roundToInt
import org.jetbrains.skia.Bitmap as SkiaBitmap
import org.jetbrains.skia.Image as SkiaImage

data class VisualElement(val file: File, var offset: Offset = Offset.Zero)

//
class TableTopState {
    val maps = mutableStateListOf<VisualElement>()
    val tokens = mutableStateListOf<VisualElement>()
    var pendingImportFile by mutableStateOf<File?>(null)

    fun onFilesDropped(files: List<File>) {
        // Wir nehmen einfach die erste Datei für den Dialog
        if (files.isNotEmpty()) {
            pendingImportFile = files.first()
        }
    }
}


@Composable
fun TableTop(state: TableTopState) { // Wir bekommen den State von außen!
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222))
    ) {
        state.maps.forEach { DraggableImage(it) }
        state.tokens.forEach { DraggableImage(it) }

        state.pendingImportFile?.let { file ->
            ImportDialog(
                file = file,
                onCancel = { state.pendingImportFile = null },
                onImport = { type ->
                    val newFile = copyFileToLibrary(file, type)
                    if (type == "Map") {
                        state.maps.add(VisualElement(newFile))
                    } else {
                        state.tokens.add(VisualElement(newFile))
                    }
                    state.pendingImportFile = null
                }
            )
        }
    }
}

@Composable
fun DraggableImage(element: VisualElement) {
    var offset by remember { mutableStateOf(element.offset) }

    // Bild laden (mit Caching via remember)
    val imageBitmap = remember(element.file) {
        loadImageFromFile(element.file)
    }

    if (imageBitmap != null) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        offset += dragAmount
                        element.offset = offset
                    }
                }
        )
    }
}

@Composable
fun ImportDialog(
    file: File,
    onCancel: () -> Unit,
    onImport: (String) -> Unit
) {
    // Abdunklung des Hintergrunds
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.6f))
            .clickable(enabled = false) {}, // Klicks abfangen
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Import File", style = MaterialTheme.typography.h6)
                Text("File: ${file.name}", style = MaterialTheme.typography.body2, modifier = Modifier.padding(8.dp))
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { onImport("Map") }) { Text("Map") }
                    Button(onClick = { onImport("Token") }) { Text("Token") }
                    OutlinedButton(onClick = onCancel) { Text("Cancel") }
                }
            }
        }
    }
}

// --- Hilfsfunktion: Bild laden (Skia Fix) ---
fun loadImageFromFile(file: File): ImageBitmap? {
    return try {
        val bytes = file.readBytes()
        val skiaImage = SkiaImage.makeFromEncoded(bytes)
        val skiaBitmap = SkiaBitmap()
        skiaBitmap.allocPixels(skiaImage.imageInfo)
        skiaImage.readPixels(skiaBitmap, 0, 0)
        skiaBitmap.asComposeImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

// --- Hilfsfunktion: Datei kopieren (JavaIO Logik) ---
fun copyFileToLibrary(sourceFile: File, type: String): File {
    val userHome = System.getProperty("user.home")
    val subFolder = if (type == "Map") "maps" else "tokens"
    val targetDir = Paths.get(userHome, "Documents", "VTT", "library", subFolder)

    if (!Files.exists(targetDir)) {
        Files.createDirectories(targetDir)
    }

    val targetPath = targetDir.resolve(sourceFile.name)
    Files.copy(sourceFile.toPath(), targetPath, StandardCopyOption.REPLACE_EXISTING)

    return targetPath.toFile()
}