
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ManageSearch
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Backpack
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import database.DBSettings
import database.DBSettings.logger
import database.ImageAssetsTable
import database.SpellsTable
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import database.SpellEntity
import database.ImageAssetEntity
import services.FileDropHandler
import services.LocalizationService
import ui.screens.MainScreen
import utils.L
import viewmodels.MainViewmodel
import viewmodels.TableTopViewmodel
import java.awt.Toolkit
import java.util.*
import kotlin.math.max

/**
 * Main entry point for the application.
 */
fun main() = application {
    val database = DBSettings.db

    transaction {
        SchemaUtils.create(SpellsTable)
        SchemaUtils.create(ImageAssetsTable)
    }

    val mainViewmodel = remember { MainViewmodel() }
    val tableTopViewmodel = remember { TableTopViewmodel() }

    val state = rememberWindowState().apply {
        // Configures window state with responsive screen-based sizing; defaults on error
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

    val isDebug = System.getenv("VTT_DEBUG")?.toBoolean() ?: false

    if (isDebug) {
        transaction(database) {
            val spellCount = SpellEntity.count()
            val imageCount = ImageAssetEntity.count()
            logger.debug("Debug mode enabled. Verbose logging enabled.")
            logger.debug("Database Connected: ${database.url}")
            logger.debug("Active Tables: Spells ($spellCount), ImageAssets ($imageCount)")
        }
    }

    Window(
        onCloseRequest = ::exitApplication,
        state = state,
        title = "VTT 0.5.0",
    ) {
        MenuBar {
            Menu(L.CREATE.t()) {
                Item(L.SPELL.t(), icon= rememberVectorPainter(Icons.Default.AutoAwesome), onClick = {
                    mainViewmodel.openSpellCreator()
                })
                Item("Item", icon = rememberVectorPainter(Icons.Default.Backpack), onClick = { mainViewmodel.openItemCreator() })
                Separator()
            }
            Menu("Search") {
                Item(L.SPELL.t(), icon=rememberVectorPainter(Icons.AutoMirrored.Filled.ManageSearch), onClick = { mainViewmodel.openSpellSearch() })
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
                Separator()
                Menu("Token Size") {
                    Item("Small (50)", onClick = { tableTopViewmodel.tokenSize = 50f })
                    Item("Medium (100)", onClick = { tableTopViewmodel.tokenSize = 100f })
                    Item("Large (150)", onClick = { tableTopViewmodel.tokenSize = 150f })
                    Item("Extra Large (200)", onClick = { tableTopViewmodel.tokenSize = 200f })
                }
                if (isDebug) {
                    Separator()
                    Item("Clear Database (Debug)", onClick = {
                        DBSettings.clearDatabase()
                        mainViewmodel.clearAll() // Should clear any open views or state
                        tableTopViewmodel.elements.clear()
                        tableTopViewmodel.recentAssets.clear()
                    })
                }
            }
        }
        FileDropHandler(onFileDrop = { file ->
            tableTopViewmodel.onFileDropped(file)
        })
        MainScreen(mainViewmodel, tableTopViewmodel)
    }
}