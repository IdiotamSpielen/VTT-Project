import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import controllers.MainController
import controllers.TableTopController
import database.DBSettings
import database.ImageAssetsTable
import database.SpellEntity
import database.SpellsTable
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import ui.MainView
import services.FileDropHandler
import services.LocalizationService
import utils.L
import java.awt.Toolkit
import java.util.Locale
import kotlin.math.max

/**
 * Main entry point for the application.
 */
fun main() = application {
    val database = DBSettings.db
    val mainController = remember { MainController() }
    val tableTopController = remember { TableTopController() }

    //Initialize DataBase
    transaction {
        SchemaUtils.create(SpellsTable)
        SchemaUtils.create(ImageAssetsTable)
    }


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

    print(database)

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "VTT 0.5.0",
    ) {
        MenuBar {
            Menu(L.CREATE.t()) {
                Item(L.SPELL.t(), icon= rememberVectorPainter(Icons.Default.AutoAwesome), onClick = {
                    mainController.openSpellCreator()
                })
                Item("Item", icon = rememberVectorPainter(Icons.Default.Backpack), onClick = { mainController.openItemCreator() })
                Separator() // Zieht eine Linie im Menü
            }
            Menu("Search") {
                Item(L.SPELL.t(), icon=rememberVectorPainter(Icons.AutoMirrored.Filled.ManageSearch), onClick = { mainController.openSpellSearch() })
            }
            Menu(L.SETTINGS.t()) {
                Menu("Language"){
                    Item("English", onClick = {
                        LocalizationService.currentLocale = Locale.US
                    })
                    Item("Deutsch", onClick = {
                        LocalizationService.currentLocale = Locale.GERMANY
                    })
                }
            }
        }
        FileDropHandler(onFileDrop = { file ->
            tableTopController.onFileDropped(file)
        })
        MainView(mainController, tableTopController)
    }
}