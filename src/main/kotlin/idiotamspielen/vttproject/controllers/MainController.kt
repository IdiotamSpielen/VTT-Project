package idiotamspielen.vttproject.controllers

import idiotamspielen.vttproject.userInterface.TableTop
import idiotamspielen.vttproject.views.SpellCreatorView
import idiotamspielen.vttproject.views.SpellSearchView
import javafx.beans.value.ChangeListener
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import tornadofx.*

/**
 * The MainController class is responsible for managing the primary user interface components and their interactions
 * within the application. It extends the base Controller class and handles setup procedures and actions related to
 * spell creation and searching.
 *
 * This class specifically manages layout resizing for the TableTop element in relation to the application's rootPane,
 * and instantiates new views for spell creation and searching.
 */
class MainController : Controller() {
    lateinit var tableTop: TableTop
    lateinit var tableTopPane: Pane
    lateinit var rootPane: Pane

    private val sizeChangeListener = ChangeListener<Number> { _, _, _ ->
        val tableTopWidth = rootPane.width * 0.765
        val tableTopHeight = rootPane.height * 0.77
        tableTop.setDimensions(tableTopWidth, tableTopHeight)
    }

    fun setup(tableTopPane: Pane, rootPane: Pane) {
        this.tableTopPane = tableTopPane
        this.rootPane = rootPane
        this.initialize()
    }

    private fun initialize() {
        val minWidth = 400.0
        val minHeight = 400.0

        tableTop = TableTop()
        tableTop.setDimensions(minWidth, minHeight)
        tableTopPane.children.add(tableTop)

        rootPane.widthProperty().addListener(sizeChangeListener)
        rootPane.heightProperty().addListener(sizeChangeListener)

        rootPane.applyCss()
        rootPane.layout()
    }

    /**
     * Opens a new window for creating spells.
     *
     * This method initializes a new `Stage` with a `SpellCreatorView` as its content.
     * The window is set with fixed dimensions and is not resizable.
     * It provides the user interface for creating and saving spells.
     */
    fun createSpell() {

        val spellCreatorView = SpellCreatorView()

        with(Stage()) {
            title = "Create Spell"
            scene = Scene(spellCreatorView.root, 500.0, 500.0)
            minWidth = 500.0
            minHeight = 500.0
            isResizable = false
            show()
        }
    }

    /**
     * Opens a new window for searching spells.
     *
     * This method creates a new `Stage` with a `SpellSearchView` as its content.
     * The window is set with specific minimum dimensions and is not resizable.
     * It initializes the spell search interface where users can input spell names
     * to search for them and view the details in a structured layout.
     */
    fun searchSpell(){
        val spellSearchView = SpellSearchView()

        with(Stage()) {
            title = "Search Spell"
            scene = Scene(spellSearchView.root, 500.0, 500.0)
            minWidth = 500.0
            minHeight = 500.0
            isResizable = false
            show()
        }
    }
}