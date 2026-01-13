
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Toolkit
import kotlin.math.max

fun main() = application { {
    private val defaultMinWidth = 800.0
    private val defaultMinHeight = 600.0

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
        title = "VTT 0.3.0 - Compose Edition",
    ) {
        MainView()
    }