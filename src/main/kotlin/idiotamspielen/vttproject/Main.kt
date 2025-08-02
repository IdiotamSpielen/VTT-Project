package idiotamspielen.vttproject

import idiotamspielen.vttproject.views.MainView
import javafx.application.Application
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.App
import kotlin.math.max

class Main : App(MainView::class) {
    override fun start(stage: Stage) {
        with(stage) {

            try {
                //A lot of logic for just... making the window a nice size
                val visualBounds = Screen.getPrimary().visualBounds

                minWidth = visualBounds.width * 0.3 // 30% of screen width
                minHeight = visualBounds.height * 0.3 // 30% of screen height

                width = max(visualBounds.width / 1.5, minWidth)
                height = max(visualBounds.height / 1.2, minHeight)
            } catch (e: Exception) {
                //magic numbers go BRR
                minWidth = 800.0
                minHeight = 600.0

                width = minWidth
                height = minHeight

                println("Error getting screen resolution: ${e.message}")
            }
        }
        super.start(stage)
        print("test")
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}