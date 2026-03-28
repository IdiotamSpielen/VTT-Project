
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
import database.*
import database.DBSettings.logger
import database.MapsTable
import org.jetbrains.exposed.v1.jdbc.SchemaUtils
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import models.*
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
        SchemaUtils.create(CharClassTable)
        SchemaUtils.create(ItemsTable)
        SchemaUtils.create(MapsTable)
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
            val spellCount = SpellsTable.selectAll().count()
            val imageCount = ImageAssetsTable.selectAll().count()
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
                Item(L.ITEM.t(), icon = rememberVectorPainter(Icons.Default.Backpack), onClick = { mainViewmodel.openItemCreator() })
                Separator()
            }
            Menu(L.SEARCH.t()) {
                Item(L.SPELL.t(), icon=rememberVectorPainter(Icons.AutoMirrored.Filled.ManageSearch), onClick = { mainViewmodel.openSpellSearch() })
            }
            Menu(L.SETTINGS.t()) {
                Menu(L.LANGUAGE.t()){
                    Item(L.LANG_EN.t(), onClick = {
                        LocalizationService.currentLocale = Locale.US
                    })
                    Item(L.LANG_DE.t(), onClick = {
                        LocalizationService.currentLocale = Locale.GERMANY
                    })
                    Item(L.LANG_ES.t(), onClick = {
                        LocalizationService.currentLocale = Locale("es", "ES")
                    })
                }
                Separator()
                Menu(L.TOKEN_SIZE.t()) {
                    Item(L.TOKEN_S.t(), onClick = { tableTopViewmodel.tokenSize = 50f })
                    Item(L.TOKEN_M.t(), onClick = { tableTopViewmodel.tokenSize = 100f })
                    Item(L.TOKEN_L.t(), onClick = { tableTopViewmodel.tokenSize = 150f })
                    Item(L.TOKEN_XL.t(), onClick = { tableTopViewmodel.tokenSize = 200f })
                }
                if (isDebug) {
                    Separator()
                    Item(L.CLEAR_DB.t(), onClick = {
                        DBSettings.clearDatabase()
                        mainViewmodel.clearAll() // Should clear any open views or state
                        tableTopViewmodel.currentMap = TableTopMap()
                        tableTopViewmodel.currentFloorIndex = 0
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