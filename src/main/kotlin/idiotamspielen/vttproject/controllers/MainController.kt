package idiotamspielen.vttproject.controllers

// MainController.kt
import idiotamspielen.vttproject.userInterface.TableTop
import idiotamspielen.vttproject.views.SpellCreatorView
import javafx.beans.value.ChangeListener
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.layout.Pane
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*
import java.io.IOException

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
}