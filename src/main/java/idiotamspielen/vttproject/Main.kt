package idiotamspielen.vttproject

import idiotamspielen.vttproject.views.MainView
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.*
import java.io.IOException
import kotlin.math.max

class Main : App(MainView::class) {
    @Throws(IOException::class)
    override fun start(stage: Stage) {
        var width = DEFAULT_WIDTH
        var height = DEFAULT_HEIGHT
        try {
            val visualBounds = Screen.getPrimary().visualBounds
            width = max(visualBounds.width / 2, DEFAULT_WIDTH)
            height = max(visualBounds.height / 2, DEFAULT_HEIGHT)
        } catch (e: Exception) {
            System.err.println("Error getting screen resolution: " + e.message)
        }
        setupUI(stage, width, height)

        stage.minWidth = 866.0
        stage.minHeight = 600.0
    }

    @Throws(IOException::class)
    private fun setupUI(primaryStage: Stage, width: Double, height: Double) {
        val mainScene = Scene(MainView().root, width, height)
        primaryStage.title = "VTT V0.3.0"
        primaryStage.scene = mainScene
        primaryStage.show()
    }


    companion object {
        private const val DEFAULT_WIDTH = 1366.0
        private const val DEFAULT_HEIGHT = 768.0
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}