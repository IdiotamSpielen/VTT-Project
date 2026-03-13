package utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadSvgPainter
import java.io.File

@Composable
fun rememberSmartPainter(filePath: String): Painter? {
    val file = File(filePath)
    val density = LocalDensity.current

    return remember(filePath) {
        try {
            if (!file.exists()) return@remember null

            if (file.extension.lowercase() == "svg") {
                file.inputStream().use { loadSvgPainter(it, density) }
            } else {
                val skiaImage = org.jetbrains.skia.Image.makeFromEncoded(file.readBytes())
                BitmapPainter(skiaImage.toComposeImageBitmap())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}