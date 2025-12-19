
import javafx.application.Application
import javafx.stage.Screen
import javafx.stage.Stage
import tornadofx.App
import views.MainView
import kotlin.math.max

class Main : App(MainView::class) {
    private val defaultMinWidth = 800.0
    private val defaultMinHeight = 600.0

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
                minWidth = defaultMinWidth
                minHeight = defaultMinHeight

                width = minWidth
                height = minHeight

                println("Error getting screen resolution: ${e.message}")
            }
        }
        super.start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(Main::class.java, *args)
}